import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Order} from '../model/order';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  constructor(private httpClient: HttpClient) { }
  addCart(order: Order): Observable<any> {
    return this.httpClient.post<Order>(`http://localhost:8080/api/order/employee/order`, order);
  }
  getCartForCustomer(customerId?: number,
                     employeeId?: number,
                     page?: number) {
    return this.httpClient.get<any>(`http://localhost:8080/api/order/customer/list/customer
    &customerId=${customerId}&employeeId=${employeeId}&page=${page}`);
  }
}
