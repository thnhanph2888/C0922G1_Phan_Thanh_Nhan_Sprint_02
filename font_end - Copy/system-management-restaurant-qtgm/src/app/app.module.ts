import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CartComponent } from './component/cart/cart.component';
import { HeaderComponent } from './component/shared/header/header.component';
import { FooterComponent } from './component/shared/footer/footer.component';
import { EmployeeListComponent } from './component/employee/employee-list/employee-list.component';
import { EmployeeCreateComponent } from './component/employee/employee-create/employee-create.component';
import { EmployeeEditComponent } from './component/employee/employee-edit/employee-edit.component';
import { CustomerCreateComponent } from './component/customer/customer-create/customer-create.component';
import { CustomerListComponent } from './component/customer/customer-list/customer-list.component';
import { CustomerEditComponent } from './component/customer/customer-edit/customer-edit.component';
import { FoodListComponent } from './component/food/food-list/food-list.component';
import { FoodEditComponent } from './component/food/food-edit/food-edit.component';
import {FoodCreateComponent} from './component/food/food-create/food-create.component';
import { HomeComponent } from './component/home/home.component';
import { OrderListComponent } from './component/order/order-list/order-list.component';
import { ChangePasswordComponent } from './component/security-authentication/change-password/change-password.component';
import { ForgotPasswordComponent } from './component/security-authentication/forgot-password/forgot-password.component';
import { RegisterComponent } from './component/security-authentication/register/register.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {SecurityAuthenticationModule} from './component/security-authentication/security-authentication.module';
import {AuthInterceptor} from './component/security-authentication/security-auth/auth.interceptor';
import {FormsModule} from '@angular/forms';
import { PersonalInformationComponent } from './component/personal-information/personal-information.component';

@NgModule({
  declarations: [
    AppComponent,
    CartComponent,
    HeaderComponent,
    FooterComponent,
    EmployeeListComponent,
    EmployeeCreateComponent,
    EmployeeEditComponent,
    CustomerCreateComponent,
    CustomerListComponent,
    CustomerEditComponent,
    FoodCreateComponent,
    FoodListComponent,
    FoodEditComponent,
    HomeComponent,
    OrderListComponent,
    ChangePasswordComponent,
    ForgotPasswordComponent,
    RegisterComponent,
    PersonalInformationComponent
  ],
    imports: [
        BrowserModule,
        HttpClientModule,
        AppRoutingModule,
        SecurityAuthenticationModule,
        FormsModule
    ],
  providers: [{provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true}],
  bootstrap: [AppComponent]
})
export class AppModule { }
