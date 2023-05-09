import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Order} from '../model/order';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  constructor(private httpClient: HttpClient) {
  }

  addCart(order: Order): Observable<any> {
    return this.httpClient.post<Order>(`http://localhost:8080/api/order/customer/addCart`, order);
  }

  getListCart(customerId?: number,
              employeeId?: number,
              page?: number,
              size?: number) {
    const params = {
      customerId,
      employeeId,
      page,
      size
    };
    const query = Object.entries(params)
      .filter(([_, value]) => value !== undefined && value !== null)
      .map(([key, value]) => `${key}=${value}`)
      .join('&');
    return this.httpClient.get<any>(`http://localhost:8080/api/order/customer/cart?${query}`);
  }

  setCartItemToOrderItem(listIdCartItem: number[]): Observable<any> {
    debugger
    return this.httpClient.post<number[]>(`http://localhost:8080/api/order/customer/setCartToOrder`, listIdCartItem);
  }
}
