import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {FoodListComponent} from './component/food/food-list/food-list.component';


const routes: Routes = [
  {
    path: 'food',
    loadChildren: () => import('./component/food/food.module').then(module => module.FoodModule)
  },
  {
    path: 'name',
    component: FoodListComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
