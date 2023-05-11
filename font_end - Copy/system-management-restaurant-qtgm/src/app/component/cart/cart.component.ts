import {Component, OnInit} from '@angular/core';
import {OrderService} from '../../service/order.service';
import {TokenStorageService} from '../security-authentication/service/token-storage.service';
import {CartItem} from '../../model/cart-item';
import {render} from 'creditcardpayments/creditCardPayments';
import Swal from 'sweetalert2';
import {ShareService} from '../security-authentication/service/share.service';
import {Order} from '../../model/order';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {
  orderPage: any;
  orderList: CartItem[];
  money = 0;
  shipFee = 20000;
  totalMoney = 0;
  size: 10;
  listIdCartItem: number[] = [];

  constructor(private orderService: OrderService,
              private tokenStorageService: TokenStorageService,
              private shareService: ShareService) {
  }

  ngOnInit(): void {
    this.getListCartItem();
  }

  calculateTotalMoneyItemSelected() {
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
      this.listIdCartItem.push(order.orderDetailId);
    });
  }

  increase(order: CartItem) {
    order.quantity++;
    this.setQuantityCartItem(order);
  }

  decrease(order: CartItem) {
    order.quantity--;
    this.setQuantityCartItem(order);
  }

  setQuantityCartItem(order: any) {
    this.orderService.setQuantityCartItem(order.orderDetailId, order.quantity).subscribe(next => {
      this.calculateTotalMoneyItemSelected();
    });
  }

  getListCartItem() {
    if (this.tokenStorageService.getRole() === 'ROLE_EMPLOYEE') {
      this.orderService.getListCartItem(this.tokenStorageService.getUserId(), true, null, this.size).subscribe(orderPage => {
        this.orderPage = orderPage;
        if (this.orderPage == null) {
          this.orderList = null;
        } else {
          this.orderList = orderPage.content;
        }
      });
    } else {
      this.orderService.getListCartItem(this.tokenStorageService.getUserId(), false, null, this.size).subscribe(orderPage => {
        this.orderPage = orderPage;
        if (this.orderPage == null) {
          this.orderList = null;
        } else {
          this.orderList = orderPage.content;
        }
      });
    }
  }

  updatePayPalControl() {
    document.querySelector('#payments').innerHTML = '';
    render({
      id: '#payments',
      currency: 'VND',
      value: this.totalMoney + '',
      onApprove: (details) => {
        this.createNewOrder();
      }
    });
  }

  createNewOrder() {
    let employeeOrder = false;
    if (this.tokenStorageService.getRole() === 'ROLE_EMPLOYEE') {
      employeeOrder = true;
    }
    const order: Order = {
      userId: this.tokenStorageService.getUser().userId,
      totalPrice: this.totalMoney,
      status: 1,
      employeeOrder
    };
    this.orderService.setCartToOrder(this.listIdCartItem, order).subscribe(next => {
      this.shareService.sendClickEvent();
      Swal.fire({
        text: 'Thanh toán thành công',
        icon: 'success',
        showConfirmButton: true
      });
      this.getListCartItem();
    });
  }

  loadMore() {
    this.size += 10;
    this.getListCartItem();
  }

  displayWarning() {
    Swal.fire({
      text: 'Vui lòng chọn món ăn muốn mua để thanh toán',
      icon: 'error',
      showConfirmButton: true
    });
  }
}
