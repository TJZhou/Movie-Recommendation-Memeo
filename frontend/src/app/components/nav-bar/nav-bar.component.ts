import { Component, OnInit } from '@angular/core';
import { UserService } from './../../services/user.service';
import { AuthService } from './../../services/auth.service';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.scss']
})
export class NavBarComponent implements OnInit {

  constructor(private auth: AuthService) { }

  ngOnInit() {
  }

  logOut() {
    localStorage.clear();
    this.auth.logout();
  }
}
