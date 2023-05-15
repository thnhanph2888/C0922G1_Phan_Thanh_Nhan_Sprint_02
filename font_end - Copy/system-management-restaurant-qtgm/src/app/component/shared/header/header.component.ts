import {Component, OnInit} from '@angular/core';
import {LoginService} from '../../security-authentication/service/login.service';
import {TokenStorageService} from '../../security-authentication/service/token-storage.service';
import {ShareService} from '../../security-authentication/service/share.service';
import {Router} from '@angular/router';
import {OrderService} from '../../../service/order.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  username: string;
  user: string;
  role: string;
  isLoggedIn = false;
  totalCartItem = 0;

  constructor(private loginService: LoginService,
              private shareService: ShareService,
              private tokenStorageService: TokenStorageService,
              private router: Router,
              private orderService: OrderService) {
  }

  ngOnInit(): void {
    this.shareService.getClickEvent().subscribe(() => {
      this.getTotalCartItem();
      this.loadHeader();
    });
    this.getTotalCartItem();
    this.loadHeader();
  }

  loadHeader(): void {
    if (this.tokenStorageService.getToken()) {
      this.role = this.tokenStorageService.getUser().roles[0];
      this.username = this.tokenStorageService.getUser().name;
      this.isLoggedIn = this.tokenStorageService.isLogger();
    }
  }

  getTotalCartItem() {
    if (this.tokenStorageService.getRole() === 'ROLE_EMPLOYEE') {
      this.orderService.getTotalCart(this.tokenStorageService.getUserId(), true).subscribe(totalCartItem => {
        this.totalCartItem = totalCartItem;
      });
    } else {
      this.orderService.getTotalCart(this.tokenStorageService.getUserId(), false).subscribe(totalCartItem => {
        this.totalCartItem = totalCartItem;
      });
    }
  }

  logOut() {
    this.tokenStorageService.signOut();
    this.username = null;
    this.role = null;
    this.shareService.sendClickEvent();
    this.router.navigateByUrl('');
    this.isLoggedIn = this.tokenStorageService.isLogger();
  }
}
