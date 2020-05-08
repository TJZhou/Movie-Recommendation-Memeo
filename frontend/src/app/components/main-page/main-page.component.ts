import { AuthService } from './../../services/auth.service';
import { Component, OnInit} from '@angular/core';
import { Router } from '@angular/router';
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

  constructor(public auth: AuthService, public movieService: MovieService, public router: Router) {
    auth.userProfile$.subscribe(res => {
      if (res !== null && res !== undefined) {
        this.username = res.name;
      }
    });
  }

  ngOnInit() {
    this.movieService.getMovieRecommendation(this.username).subscribe(res => {
      const response: MovieResponse = res;
      this.movies = response.data;
      this.isLoading = false;
    });
  }

  logOut() {
    this.auth.logout();
    // this.router.navigate(['login']);
  }

  test() {
    console.log(this.movies);
  }
}
