import { ErrorPageComponent } from './error-page/error-page.component';
import { AuthGuard } from './services/auth-guard.service';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { MainPageComponent } from './components/main-page/main-page.component';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { InterceptorService } from './services/request-interceptor.service';

const routes: Routes = [
  { path: '', component: MainPageComponent, canActivate: [AuthGuard]},
  { path: 'main', component: MainPageComponent, canActivate: [AuthGuard]},
  { path: 'login', component: LoginComponent},
  { path: '**', component: ErrorPageComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: InterceptorService,
      multi: true
    }
  ]
})
export class AppRoutingModule { }
