import { NumberResponse } from './../models/response.model';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Movie, MovieResponse } from '../models/movie.model';

@Injectable({
  providedIn: 'root'
})
export class MovieService {

  private movieUrl = environment.apiUrl + '/movie/';

  constructor(private http: HttpClient) { }

  public getMovieRecommendation(userId: number): Observable<MovieResponse> {
    return this.http.get<MovieResponse>(this.movieUrl + userId);
  }

  public getRealtimeMovieRecommendation(userId: number): Observable<MovieResponse> {
    return this.http.get<MovieResponse>(this.movieUrl + 'recommend/' + userId);
  }

  public getMoviesByGenre(genre: string, userId: number, page: number): Observable<MovieResponse> {
    const params = {
      'genre': genre,
      'userId': userId.toString(),
      'page': page.toString()
    };
    return this.http.get<MovieResponse>(this.movieUrl + 'genre', {params});
  }

  public countMoviesByGenre(genre: string): Observable<NumberResponse> {
    const params = {
      'genre': genre,
    };
    return this.http.get<NumberResponse>(this.movieUrl + 'count/genre', {params});
  }

  public updateMovieRating(userId: number, movie: Movie): Observable<void> {
    return this.http.put<void>(this.movieUrl + userId, movie);
  }
}
