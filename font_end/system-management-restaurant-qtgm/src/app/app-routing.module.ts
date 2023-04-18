import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import {HomeComponent} from './component/home/home.component';


const routes: Routes = [
  {
    path: 'food',
    loadChildren: () => import('./component/food/food.module').then(module => module.FoodModule)
  },
  {
    path: '',
    component: HomeComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
