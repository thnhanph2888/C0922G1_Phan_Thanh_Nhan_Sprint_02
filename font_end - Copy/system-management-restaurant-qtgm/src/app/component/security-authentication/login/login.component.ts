import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';
import {LoginService} from '../service/login.service';
import {Router} from '@angular/router';
import {TokenStorageService} from '../service/token-storage.service';
import {ShareService} from '../service/share.service';
import Swal from 'sweetalert2';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;

  constructor(private loginService: LoginService,
              private router: Router,
              private tokenStorageService: TokenStorageService,
              private shareService: ShareService) {
  }

  ngOnInit(): void {
    if (this.loginService.isLoggedIn) {
      Swal.fire({
        text: 'Bạn đã đăng nhập.',
        icon: 'warning',
        showConfirmButton: false,
        timer: 1500
      });
      this.router.navigateByUrl('/');
    }
    this.view();
    this.loginForm = new FormGroup({
      username: new FormControl(''),
      password: new FormControl('')
    });
    if (this.tokenStorageService.getToken()) {
      this.loginService.isLoggedIn = true;
    }
  }


  view(): void {
    const element = document.getElementById('login');
    if (element) {
      element.scrollIntoView();
    }
  }

  onSubmit() {
    this.loginService.login(this.loginForm.value).subscribe(data => {
        if (this.loginForm.value.rememberMe) {
          this.tokenStorageService.saveTokenLocal(data.accessToken);
          this.tokenStorageService.saveUserLocal(data);
        } else {
          this.tokenStorageService.saveTokenSession(data.accessToken);
          this.tokenStorageService.saveUserLocal(data);
        }
        this.loginForm.reset();
        Swal.fire({
          text: 'Đăng nhập thành công',
          icon: 'success',
          showConfirmButton: false,
          timer: 1500
        });
        this.router.navigateByUrl('/food/list');
        this.shareService.sendClickEvent();
      },
      err => {
        this.loginService.isLoggedIn = false;
        Swal.fire({
          text: 'Tài khoản, mật khẩu không đúng hoặc chưa được kích hoạt!',
          icon: 'error',
          confirmButtonText: 'OK'
        });
      }
    );
  }
}
