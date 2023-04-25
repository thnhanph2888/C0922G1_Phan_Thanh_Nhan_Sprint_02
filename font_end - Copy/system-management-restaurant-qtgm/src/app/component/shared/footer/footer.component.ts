import {Component, OnInit} from '@angular/core';
import {LoginService} from '../../security-authentication/service/login.service';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})
export class FooterComponent implements OnInit {

  constructor() {
  }

  ngOnInit(): void {
  }

}
