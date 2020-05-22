import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  UrlTree,
  CanActivate,
  Router,
} from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';
import { tap, filter, take, switchMap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate {
  constructor(private auth: AuthService) {}

  waitForHandleAuthCallbackToComplete(): Observable<boolean> {
    return this.auth.handleAuthCallbackComplete$.pipe(
      filter(complete => complete),
      take(1),
    );
  }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean> | Promise<boolean | UrlTree> | boolean {
    return this.waitForHandleAuthCallbackToComplete().pipe(
      switchMap(() =>
        this.auth.isAuthenticated$.pipe(
          tap(loggedIn => {
            if (!loggedIn) {
              this.auth.login(state.url);
            }
          }),
        ),
      ),
    );
  }
}
