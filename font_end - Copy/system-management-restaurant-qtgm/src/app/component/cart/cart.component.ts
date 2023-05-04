import {Component, OnInit} from '@angular/core';
import {OrderService} from '../../service/order.service';
import {TokenStorageService} from '../security-authentication/service/token-storage.service';
import {OrderCustomer} from '../../model/order-customer';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {
  orderPage: any;
  orderList: OrderCustomer[];
  constructor(private orderService: OrderService,
              private tokenStorageService: TokenStorageService) {
  }

  ngOnInit(): void {
    if (this.tokenStorageService.getRole() === 'ROLE_CUSTOMER') {
      this.orderService.getCartForCustomer(this.tokenStorageService.getUserId(), null, null).subscribe(orderPage => {
            this.orderPage = orderPage;
            this.orderList = orderPage.content;
      });
    }
  }
}
