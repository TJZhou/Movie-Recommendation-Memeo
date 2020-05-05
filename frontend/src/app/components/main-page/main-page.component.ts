import { AuthService } from './../../services/auth.service';
import { Component, OnInit } from '@angular/core';
import { Movie } from './../../models/movie.model';
import { Response } from './../../models/response.model';
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

  constructor(public auth: AuthService, public movieService: MovieService) {
    auth.userProfile$.subscribe(res => {
      if (res !== null && res !== undefined) {
        this.username = res.name;
      }
    });
  }

  ngOnInit() {
    this.movieService.getMovieRecommendation(this.username).subscribe(res => {
      const response: Response = res;
      this.movies = response.data as Movie[];
      this.isLoading = false;
    });
  }

  test() {
    console.log(this.movies);
  }
}
