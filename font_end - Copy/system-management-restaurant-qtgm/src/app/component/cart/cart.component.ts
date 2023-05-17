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
  cartItemList: CartItem[];
  money = 0;
  shipFee = 20000;
  totalMoney = 0;
  size = 10;
  hasNext: boolean;
  hasContent: boolean;
  isCheckSuccess: boolean;
  deleteId: number;
  setAll = false;
  constructor(private orderService: OrderService,
              private tokenStorageService: TokenStorageService,
              private shareService: ShareService) {
  }

  ngOnInit(): void {
    this.getListCartItem();
  }

  setAllSelected() {
    if (this.setAll) {
      this.setAll = false;
      this.cartItemList.map(cartItem => cartItem.isSelected = false);
      this.calculateTotalMoneyItemSelected();
    } else {
      this.setAll = true;
      this.cartItemList.map(cartItem => cartItem.isSelected = true);
      this.calculateTotalMoneyItemSelected();
    }
  }

  calculateTotalMoneyItemSelected() {
    this.totalMoney = 0;
    const selectedCartItem = this.cartItemList.filter(order => order.isSelected);
    selectedCartItem.forEach(order => {
      this.totalMoney += order.quantity * order.price;
    });
  }

  getIdCartItem(): number[] {
    return this.cartItemList
      .filter(order => order.isSelected)
      .map(order => order.orderDetailId);
  }

  increase(order: CartItem) {
    if (this.checkQuantity(order)) {
      order.quantity++;
      this.setQuantityCartItem(order);
    }
  }

  decrease(order: CartItem) {
    order.quantity--;
    this.setQuantityCartItem(order);
  }

  getQuantityInput(order: CartItem) {
    if (this.checkQuantity(order)) {
      this.setQuantityCartItem(order);
    } else {
      order.quantity = order.quantityFood;
      this.setQuantityCartItem(order);
    }
  }

  checkQuantity(order: CartItem): boolean {
    if (order.quantity >= order.quantityFood) {
      Swal.fire({
        text: 'Xin lỗi nhà hàng chúng tôi không còn đủ số lượng, quý khách vui lòng xem món ăn khác hoặc sau lại sau',
        icon: 'error',
        showConfirmButton: true
      });
      return false;
    } else {
      return true;
    }
  }

  setQuantityCartItem(order: CartItem) {
    this.orderService.setQuantityCartItem(order.orderDetailId, order.quantity).subscribe(next => {
      this.calculateTotalMoneyItemSelected();
    });
  }

  getListCartItem() {
    if (this.tokenStorageService.getRole() === 'ROLE_EMPLOYEE') {
      this.orderService.getListCartItem(this.tokenStorageService.getUserId(), true, null, this.size).subscribe(orderPage => {
        this.setNewPageCartItem(orderPage);
      });
    } else {
      this.orderService.getListCartItem(this.tokenStorageService.getUserId(), false, null, this.size).subscribe(orderPage => {
        this.setNewPageCartItem(orderPage);
      });
    }
  }

  setNewPageCartItem(page: any) {
    this.orderPage = page;
    if (this.orderPage == null) {
      this.cartItemList = null;
      this.hasNext = false;
      this.hasContent = false;
    } else {
      this.hasNext = this.orderPage.number + 1 < this.orderPage.totalPages;
      this.cartItemList = page.content;
      this.hasContent = true;
    }
  }

  updatePayPalControl() {
    const listCartItemId = this.getIdCartItem();
    this.orderService.checkQuantityFood(listCartItemId).subscribe(next => {
      this.isCheckSuccess = true;
      document.querySelector('#payments').innerHTML = '';
      render({
        id: '#payments',
        currency: 'VND',
        value: String((this.totalMoney / 22000).toFixed(2)),
        onApprove: (details) => {
          this.createNewOrder();
        }
      });
    }, error => {
      this.isCheckSuccess = false;
    });
  }

  createNewOrder() {
    const listCartItemId = this.getIdCartItem();
    const orderNew = this.getObject();
    this.orderService.setCartToOrder(listCartItemId, orderNew).subscribe(statusResult => {
      if (statusResult === 0) {
        this.shareService.sendClickEvent();
        Swal.fire({
          text: 'Thanh toán thành công',
          icon: 'success',
          showConfirmButton: true
        });
        this.getListCartItem();
      } else {
        Swal.fire({
          text: 'Món ăn quý khách chọn không còn đủ số lượng, vui lòng chọn lại số lượng!',
          icon: 'success',
          showConfirmButton: true
        });
      }
    });
  }

  getObject(): Order {
    let employeeOrder = false;
    if (this.tokenStorageService.getRole() === 'ROLE_EMPLOYEE') {
      employeeOrder = true;
    }
    return {
      userId: this.tokenStorageService.getUser().userId,
      totalPrice: this.totalMoney,
      status: 1,
      employeeOrder
    };
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

  setIdDelete(id: number) {
     this.deleteId = id;
  }

  delete() {
    this.orderService.deleteOrderDetailById(this.deleteId).subscribe(next => {
      Swal.fire({
        text: 'Đã xóa khỏi giỏ hàng',
        icon: 'success',
        showConfirmButton: true
      });
      this.getListCartItem();
    }, error => {
      Swal.fire({
        text: 'Lỗi',
        icon: 'error',
        showConfirmButton: true
      });
    });
  }
}
