import { AuthService } from './../../services/auth.service';
import { Component, OnInit} from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Movie, MovieResponse } from '../../models/movie.model';
import { MovieService } from './../../services/movie.service';

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.scss']
})


export class MainPageComponent implements OnInit {

  username: string;
  isLoading = true;
  movies: Movie[];
  genres = ['Action', 'Comedy', 'Thriller', 'Crime', 'Sci-Fi', 'Animation', 'Fantasy', 'Drama', 'Romance'];
  queryParams: string;

  constructor(public auth: AuthService,
              public movieService: MovieService,
              public activatedRoute: ActivatedRoute,
              public router: Router) {
    auth.userProfile$.subscribe(res => {
      if (res !== null && res !== undefined) {
        this.username = res.name;
      }
    });
    this.activatedRoute.queryParams.subscribe(params => {
      // tslint:disable-next-line:no-string-literal
      this.queryParams = params['genre'];
    });
  }

  ngOnInit() {
      if (this.queryParams === 'Recommended') {
        this.getMovieRecommendation();
      } else {
        this.listMoviesByGenre(this.queryParams);
      }
  }

  getMovieRecommendation() {
    this.router.navigate([], {queryParams: {genre: 'Recommended'}});
    this.movieService.getMovieRecommendation(this.username).subscribe(res => {
      const response: MovieResponse = res;
      this.movies = response.data;
      this.isLoading = false;
    }, err => {
      console.log(err);
    });
  }

  listMoviesByGenre(ele: string) {
    this.router.navigate([], {queryParams: {genre: ele}});
    this.isLoading = true;
    this.movieService.getMoviesByGenre(ele, 0).subscribe(res => {
      const response: MovieResponse = res;
      this.movies = response.data;
      this.isLoading = false;
    }, err => {
      console.log(err);
    });
  }

  clickMovieLink(ele) {
    // to-do send user log to flume
  }
}
