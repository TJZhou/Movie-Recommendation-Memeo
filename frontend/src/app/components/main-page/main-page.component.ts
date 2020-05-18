import { UserService } from './../../services/user.service';
import { AuthService } from './../../services/auth.service';
import { Component, OnInit} from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Movie, MovieResponse } from '../../models/movie.model';
import { NumberResponse } from './../../models/response.model';
import { MovieService } from './../../services/movie.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { User } from 'src/app/models/user.model';
@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.scss']
})

export class MainPageComponent implements OnInit {

  username: string;
  userId: number;
  isLoading = true;
  movies: Movie[];
  genres = ['Action', 'Comedy', 'Thriller', 'Crime', 'Sci-Fi', 'Animation', 'Fantasy', 'Drama', 'Romance'];
  queryParamGenre: string;
  prevGenreSelected: string;
  page: number;
  maxPage: number;
  pageArr: Array<number>;
  sizePerPage = 30;

  constructor(public auth: AuthService,
              public movieService: MovieService,
              public userService: UserService,
              public activatedRoute: ActivatedRoute,
              public router: Router,
              public errorMessage: MatSnackBar) {
                this.page = 1;
                this.isLoading = true;
              }

  ngOnInit() {
    this.activatedRoute.queryParams.subscribe(params => {
      this.queryParamGenre = params['genre'];
    });
    // get username and userId; if the localStorage is null then create user
    this.auth.userProfile$.subscribe(async res => {
      this.username = res.name;
      if (localStorage.getItem('userId') === undefined || localStorage.getItem('userId') == null) {
          const user = new User();
          user.userId = null;
          user.username = this.username;
          await this.userService.createUser(user).subscribe(resp => {
            localStorage.setItem('userId', resp.data[0].userId.toString());
            this.userId = parseInt(localStorage.getItem('userId'), 10);
            if (this.queryParamGenre === 'Recommended' || this.queryParamGenre === undefined) {
              this.getMovieRecommendation();
            } else {
              this.listMoviesByGenre(this.queryParamGenre);
            }
          });
      } else {
        this.userId = parseInt(localStorage.getItem('userId'), 10);
        if (this.queryParamGenre === 'Recommended' || this.queryParamGenre === undefined) {
          this.getMovieRecommendation();
        } else {
          this.listMoviesByGenre(this.queryParamGenre);
        }
      }
    });
  }

  getMovieRecommendation(): void {
    this.router.navigate([], {queryParams: {'genre': 'Recommended'}});
    this.page = 1;
    this.pageArr = [1];
    this.movieService.getMovieRecommendation(this.userId).subscribe(res => {
      const response: MovieResponse = res;
      this.movies = response.data;
      this.isLoading = false;
    }, err => {
      console.log(err);
    });
  }

  getNumberOfMovies(genre: string): void {
    this.movieService.countMoviesByGenre(genre).subscribe(res => {
      const response: NumberResponse = res;
      this.maxPage = Math.floor((response.data[0] - 1) / this.sizePerPage) + 1;
      this.pageArr = new Array();
      for (let i = 1; i <= this.maxPage; i++) {
        this.pageArr.push(i);
      }
    }, err => {
      console.log(err);
    });
  }

  listMoviesByGenre(genre: string): void {
    // change movie category or first login (oreGenreSelected is undefined)
    if (this.prevGenreSelected !== genre) {
      this.page = 1;
      this.getNumberOfMovies(genre);
    }
    this.prevGenreSelected = genre;
    this.router.navigate([], {queryParams: {'genre': genre}});
    this.movieService.getMoviesByGenre(genre, this.userId, this.page - 1).subscribe(res => {
      const response: MovieResponse = res;
      this.movies = response.data;
      this.isLoading = false;
    }, err => {
      console.log(err);
    });
  }

  goToPage(newPage): void {
    if (this.queryParamGenre === 'Recommended' || this.queryParamGenre === undefined) {
      this.errorMessage.open('No more recommended movies', 'error', {
        duration: 1800,
      });
      return;
    }
    if (newPage < 1) {
      this.errorMessage.open('Already on the first page', 'error', {
        duration: 1800,
      });
    } else if (newPage > this.maxPage) {
      this.errorMessage.open('Already on the last page', 'error', {
        duration: 1800,
      });
    } else {
      console.log(newPage);
      this.page = newPage;
      this.listMoviesByGenre(this.prevGenreSelected);
    }
  }

  rateTheMovie(event: any, movie: Movie): void {
    movie.userRating = event.rating;
    this.movieService.updateMovieRating(this.userId, movie).subscribe();
  }

  logout() {
    localStorage.clear();
    this.auth.logout();
  }
}
