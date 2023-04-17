import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CartComponent } from './component/cart/cart.component';
import { OrderComponent } from './component/order/order.component';
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

@NgModule({
  declarations: [
    AppComponent,
    CartComponent,
    OrderComponent,
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
    FoodEditComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
