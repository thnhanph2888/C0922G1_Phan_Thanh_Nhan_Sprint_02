import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SecurityAuthenticationRoutingModule } from './security-authentication-routing.module';
import { LoginComponent } from './login/login.component';
import {ReactiveFormsModule} from '@angular/forms';


@NgModule({
  declarations: [LoginComponent],
    imports: [
        CommonModule,
        SecurityAuthenticationRoutingModule,
        ReactiveFormsModule
    ]
})
export class SecurityAuthenticationModule { }
