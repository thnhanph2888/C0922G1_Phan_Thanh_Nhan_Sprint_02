import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {TokenStorageService} from '../security-authentication/service/token-storage.service';

@Component({
  selector: 'app-personal-information',
  templateUrl: './personal-information.component.html',
  styleUrls: ['./personal-information.component.css']
})
export class PersonalInformationComponent implements OnInit {

  constructor(private tokenStorageService: TokenStorageService) {
  }

  ngOnInit(): void {
  }

}
