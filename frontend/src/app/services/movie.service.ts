import { NumberResponse } from './../models/response.model';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { MovieResponse } from '../models/movie.model';

@Injectable({
  providedIn: 'root'
})
export class MovieService {

  private movieUrl = environment.apiUrl + '/movie/';

  constructor(private http: HttpClient) { }

  public getMovieRecommendation(userId: string): Observable<MovieResponse> {
    return this.http.get<MovieResponse>(this.movieUrl + userId);
  }

  public getMoviesByGenre(genre: string, page: number): Observable<MovieResponse> {
    const params = {
      'genre': genre,
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
}
