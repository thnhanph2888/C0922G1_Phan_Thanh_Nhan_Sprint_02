import { Component, OnInit } from '@angular/core';
import {LoginService} from '../../security-authentication/service/login.service';
import {TokenStorageService} from '../../security-authentication/service/token-storage.service';
import {ShareService} from '../../security-authentication/service/share.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  username: string;
  currentUser: string;
  user: string;
  role: string;
  isLoggedIn = false;
  constructor(private loginService: LoginService,
              private shareService: ShareService,
              private tokenStorageService: TokenStorageService,
              private router: Router) { }

  ngOnInit(): void {
    this.shareService.getClickEvent().subscribe(() => {
      this.loadHeader();
    });
    this.loadHeader();
  }
  addToken() {
    const sessionId = 'session_id';
    const token = 'access_token';
    const expireAt = new Date();
    this.loginService.addTokenToBlacklist(sessionId, token, expireAt).subscribe(
      () => {
        console.log('Token added to blacklist.');
      },
      (error) => {
        console.log('Error adding token to blacklist: ' + error.message);
      }
    );
  }

  loadHeader(): void {
    if (this.tokenStorageService.getToken()) {
      this.currentUser = this.tokenStorageService.getUser().username;
      this.role = this.tokenStorageService.getUser().roles[0];
      this.username = this.tokenStorageService.getUser().name;
    }
    this.isLoggedIn = this.username != null;
  }

  logOut() {
    this.tokenStorageService.signOut();
    this.username = null;
    this.role = null;
    this.loginService.setStatusLogin(false);
    this.shareService.sendClickEvent();
    this.router.navigateByUrl('');
  }
}
