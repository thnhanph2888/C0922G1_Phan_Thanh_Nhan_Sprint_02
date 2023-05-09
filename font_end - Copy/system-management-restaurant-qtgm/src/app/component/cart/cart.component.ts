import {Component, OnInit} from '@angular/core';
import {OrderService} from '../../service/order.service';
import {TokenStorageService} from '../security-authentication/service/token-storage.service';
import {OrderCustomer} from '../../model/order-customer';
import {render} from 'creditcardpayments/creditCardPayments';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {
  orderPage: any;
  orderList: OrderCustomer[];
  money = 0;
  shipFee = 20000;
  totalMoney = 0;
  size: 10;
  listIdCartItem: number[] = [];
  payPalControl: any;

  constructor(private orderService: OrderService,
              private tokenStorageService: TokenStorageService) {
  }

  ngOnInit(): void {
    this.getListCart();
  }

  getSelectedCartItem() {
    this.totalMoney = 0;
    const selectedCartItem = this.orderList.filter(order => order.isSelected);
    selectedCartItem.forEach(order => {
      this.totalMoney += order.quantity * order.price;
    });
    if (this.totalMoney > 0) {
      this.updatePayPalControl();
    }
  }

  getIdCartItem() {
    const selectedCartItem = this.orderList.filter(order => order.isSelected);
    selectedCartItem.forEach(order => {
      this.listIdCartItem.push(order.id);
    });
  }

  increase(order: OrderCustomer) {
    order.quantity++;
    this.getSelectedCartItem();
  }

  decrease(order: OrderCustomer) {
    order.quantity--;
    this.getSelectedCartItem();
  }

  getListCart() {
    if (this.tokenStorageService.getRole() === 'ROLE_EMPLOYEE') {
      this.orderService.getListCart(null, this.tokenStorageService.getUserId(), null, this.size).subscribe(orderPage => {
        this.orderPage = orderPage;
        this.orderList = orderPage.content;
      });
    }

    this.orderService.getListCart(this.tokenStorageService.getUserId(), null, null, this.size).subscribe(orderPage => {
      this.orderPage = orderPage;
      this.orderList = orderPage.content;
    });
  }

  updatePayPalControl() {
      document.querySelector('#payments').innerHTML = '';
      render({
        id: '#payments',
        currency: 'VND',
        value: this.totalMoney + '',
        onApprove: (details) => {
          this.setStatusCartAfterPay();
        }
      });
  }

  setStatusCartAfterPay() {
      this.orderService.setCartItemToOrderItem(this.listIdCartItem).subscribe(next => {
        Swal.fire({
          text: 'Thanh toán thành công',
          icon: 'success',
          showConfirmButton: true
        });
        this.getListCart();
      });
  }

  loadMore() {
    this.size += 10;
    this.getListCart();
  }

  displayWarning() {
    Swal.fire({
      text: 'Vui lòng chọn món ăn muốn mua để thanh toán',
      icon: 'error',
      showConfirmButton: true
    });
  }
}
