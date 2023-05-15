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

  addCart(order: Order): Observable<number> {
    return this.httpClient.post<number>(`http://localhost:8080/api/order/customer/addCart`, order);
  }

  getListCartItem(customerId?: number,
                  employeeId?: boolean,
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
    return this.httpClient.get<any>(`http://localhost:8080/api/order/customer/getCart?${query}`);
  }

  getOrderListOfUser(customerId?: number,
                     employeeId?: boolean,
                     page?: number,
                     size?: number): Observable<any> {
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
    return this.httpClient.get<any>(`http://localhost:8080/api/order/customer/getOrder?${query}`);
  }

  checkQuantityFood(listIdCartItem: number[]): Observable<any> {
    return this.httpClient.post<any>(`http://localhost:8080/api/order/customer/checkQuantityFood`, listIdCartItem);
  }

  setCartToOrder(listIdCartItem: number[], order: Order): Observable<any> {
    const data = {
      listIdCartItem,
      order
    };
    return this.httpClient.post<any>(`http://localhost:8080/api/order/customer/createNewOrder`, data);
  }

  getTotalCart(customerId?: number,
               isEmployeeOrder?: boolean) {
    const params = {
      customerId,
      isEmployeeOrder
    };
    const query = Object.entries(params)
      .filter(([_, value]) => value !== undefined && value !== null)
      .map(([key, value]) => `${key}=${value}`)
      .join('&');
    return this.httpClient.get<any>(`http://localhost:8080/api/order/customer/totalCart?${query}`);
  }

  setQuantityCartItem(cartItemId: number, quantity: number): Observable<any> {
    // tslint:disable-next-line:max-line-length
    return this.httpClient.get<any>(`http://localhost:8080/api/order/customer/setQuantityCartItem?cartItemId=${cartItemId}&quantity=${quantity}`);
  }

  deleteOrderDetailById(id: number): Observable<any> {
    return this.httpClient.get<any>(`http://localhost:8080/api/order/customer/removeCartItem/${id}`);
  }
}
