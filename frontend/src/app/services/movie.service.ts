import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Response } from '../models/response.model';

@Injectable({
  providedIn: 'root'
})
export class MovieService {

  private movieUrl = environment.apiUrl + '/movie/';

  constructor(private http: HttpClient) { }

  public getMovieRecommendation(userId: string): Observable<Response> {
    return this.http.get<Response>(this.movieUrl + userId);
  }
}
