import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import {HomeComponent} from './component/home/home.component';
import {CartComponent} from './component/cart/cart.component';


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
    path: 'login',
    loadChildren: () => import('./component/login/login/login.module.js').then(module => module.LoginModule)
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
