import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import {HomeComponent} from './component/home/home.component';
import {CartComponent} from './component/cart/cart.component';
import {AdminGuard} from './component/security-authentication/security-auth/admin.guard';
import {EmployeeGuard} from './component/security-authentication/security-auth/employee.guard';


const routes: Routes = [
  {
    path: '',
    component: HomeComponent
  },
  {
    path: 'food',
    loadChildren: () => import('./component/food/food.module').then(module => module.FoodModule)
  },
  {
    path: 'order',
    loadChildren: () => import('./component/order/order.module').then(module => module.OrderModule)
  },
  {
    path: 'customer',
    loadChildren: () => import('./component/customer/customer.module').then(module => module.CustomerModule),
    canActivate: [AdminGuard, EmployeeGuard]
  },
  {
    path: 'employee',
    loadChildren: () => import('./component/employee/employee.module').then(module => module.EmployeeModule),
    canActivate: [AdminGuard]
  },
  {
    path: 'security',
    loadChildren: () => import('./component/security-authentication/security-authentication-routing.module')
      .then(module => module.SecurityAuthenticationRoutingModule)
  },
  {
    path: 'cart',
    component: CartComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
