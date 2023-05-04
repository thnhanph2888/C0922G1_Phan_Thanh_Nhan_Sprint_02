import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {OrderCreateComponent} from './order-create/order-create.component';
import {OrderListComponent} from './order-list/order-list.component';
import {OrderEditComponent} from './order-edit/order-edit.component';


const routes: Routes = [
  {
    path: 'create',
    component: OrderCreateComponent
  },
  {
    path: 'list',
    component: OrderListComponent
  },
  {
    path: 'edit/:id',
    component: OrderEditComponent
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class OrderRoutingModule { }
