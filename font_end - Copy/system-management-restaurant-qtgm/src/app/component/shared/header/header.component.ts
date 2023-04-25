import { Component, OnInit } from '@angular/core';
import {LoginService} from '../../security-authentication/service/login.service';
import {TokenStorageService} from '../../security-authentication/service/token-storage.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(private logoutService: LoginService,
              private tokenStorageService: TokenStorageService) { }

  ngOnInit(): void {
  }
  addToken() {
    const sessionId = 'session_id';
    const token = 'access_token';
    const expireAt = new Date();
    this.logoutService.addTokenToBlacklist(sessionId, token, expireAt).subscribe(
      () => {
        console.log('Token added to blacklist.');
      },
      (error) => {
        console.log('Error adding token to blacklist: ' + error.message);
      }
    );
  }

  logOut() {
    this.tokenStorageService.signOut();
  }
}
