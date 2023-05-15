import {Component, OnInit} from '@angular/core';
import {CartItem} from '../../../model/cart-item';
import {render} from 'creditcardpayments/creditCardPayments';
import Swal from 'sweetalert2';
import {OrderService} from '../../../service/order.service';
import {TokenStorageService} from '../../security-authentication/service/token-storage.service';
import {ShareService} from '../../security-authentication/service/share.service';
import {Order} from '../../../model/order';

@Component({
  selector: 'app-order-list',
  templateUrl: './order-list.component.html',
  styleUrls: ['./order-list.component.css']
})
export class OrderListComponent implements OnInit {
  size = 10;
  orderPage: any;
  orderList: Order[];

  constructor(private orderService: OrderService,
              private tokenStorageService: TokenStorageService) {
  }

  ngOnInit(): void {
    this.getOrderList();
  }

  loadMore() {
    this.size += 10;
    this.getOrderList();
  }

  private getOrderList() {
    if (this.tokenStorageService.getRole() === 'ROLE_EMPLOYEE') {
      this.orderService.getOrderListOfUser(this.tokenStorageService.getUserId(), true, null, this.size).subscribe(orderPage => {
        debugger
        this.orderPage = orderPage;
        if (this.orderPage == null) {
          this.orderList = null;
        } else {
          this.orderList = orderPage.content;
        }
      });
    } else {
      this.orderService.getOrderListOfUser(this.tokenStorageService.getUserId(), false, null, this.size).subscribe(orderPage => {
        debugger
        this.orderPage = orderPage;
        if (this.orderPage == null) {
          this.orderList = null;
        } else {
          this.orderList = orderPage.content;
        }
      });
    }
  }
}
