import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {FoodListComponent} from './food-list/food-list.component';
import {FoodDetailComponent} from './food-detail/food-detail.component';
import {FoodCreateComponent} from './food-create/food-create.component';
import {FoodEditComponent} from './food-edit/food-edit.component';


const routes: Routes = [
  {
    path: 'create',
    component: FoodCreateComponent
  },
  {
    path: 'list',
    component: FoodListComponent
  },
  {
    path: 'detail/:id',
    component: FoodDetailComponent
  },
  {
    path: 'edit/:id',
    component: FoodEditComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FoodRoutingModule { }
