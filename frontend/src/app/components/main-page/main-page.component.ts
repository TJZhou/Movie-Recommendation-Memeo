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
  movieRecommendations: Movie[];

  tiles: Tile[] = [
    {text: 'One', cols: 3, rows: 1, color: 'lightblue'},
    {text: 'Two', cols: 1, rows: 2, color: 'lightgreen'},
    {text: 'Three', cols: 1, rows: 1, color: 'lightpink'},
    {text: 'Four', cols: 2, rows: 1, color: '#DDBDF1'},
  ];

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
      console.log(res);
      this.movieRecommendations = response.data;
      this.isLoading = false;
    });
  }

  test() {
    console.log(this.movieRecommendations);
  }
}
