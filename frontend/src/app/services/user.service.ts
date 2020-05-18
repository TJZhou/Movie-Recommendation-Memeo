import { User, UserResponse } from './../models/user.model';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})

export class UserService {

  private userUrl = environment.apiUrl + '/user/';

  constructor(private http: HttpClient) { }

  public getUserByUserName(username: string): Observable<UserResponse> {
    return this.http.get<UserResponse>(this.userUrl + 'username/' + username);
  }

  public createUser(user: User): Observable<UserResponse> {
    return this.http.post<UserResponse>(this.userUrl, user);
  }
}
