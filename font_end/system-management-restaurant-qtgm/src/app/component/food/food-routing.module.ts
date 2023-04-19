import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {FoodListComponent} from './food-list/food-list.component';
import {FoodDetailComponent} from './food-detail/food-detail.component';


const routes: Routes = [
  {
    path: 'list',
    component: FoodListComponent
  },
  {
    path: 'detail/:id',
    component: FoodDetailComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FoodRoutingModule { }
