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
}
