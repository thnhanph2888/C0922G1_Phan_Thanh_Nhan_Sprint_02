import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {CustomerCreateComponent} from './customer-create/customer-create.component';
import {CustomerListComponent} from './customer-list/customer-list.component';
import {CustomerEditComponent} from './customer-edit/customer-edit.component';


const routes: Routes = [
  {
    path: 'create',
    component: CustomerCreateComponent
  },
  {
    path: 'list',
    component: CustomerListComponent
  },
  {
    path: 'edit/:id',
    component: CustomerEditComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CustomerRoutingModule { }
