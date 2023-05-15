import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {FoodService} from '../../../service/food.service';
import {Food} from '../../../model/food';
import {render} from 'creditcardpayments/creditCardPayments';
import Swal from 'sweetalert2';
import {OrderService} from '../../../service/order.service';
import {Order} from '../../../model/order';
import {TokenStorageService} from '../../security-authentication/service/token-storage.service';

@Component({
  selector: 'app-food-detail',
  templateUrl: './food-detail.component.html',
  styleUrls: ['./food-detail.component.css']
})
export class FoodDetailComponent implements OnInit {
  foodDetail: Food;
  quantity = 1;
  totalMoney: number;

  constructor(private activatedRoute: ActivatedRoute,
              private foodService: FoodService,
              private orderService: OrderService,
              private tokenStorageService: TokenStorageService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(paramMap => {
      const id = +paramMap.get('id');
      this.foodService.findFoodById(id).subscribe(foodDetail => {
        this.foodDetail = foodDetail;
      });
    });
  }

  updatePayPalControl() {
    document.querySelector('#payments').innerHTML = '';
    render({
      id: '#payments',
      currency: 'VND',
      value: this.totalMoney + '',
      onApprove: (details) => {
        // this.createNewOrder();
      }
    });
  }

  increase() {
    if (this.quantity < 100) {
      this.quantity++;
    }
  }

  decrease() {
    if (this.quantity > 1) {
      this.quantity--;
    }
  }

  addToCart() {
    let employeeOrder = false;
    if (this.tokenStorageService.getRole() === 'ROLE_EMPLOYEE') {
      employeeOrder = true;
    }
    const orderCart: Order = {
      userId: this.tokenStorageService.getUser().userId,
      foodId: this.foodDetail.id,
      quantity: this.quantity,
      status: 0,
      employeeOrder
    };
    this.orderService.addCart(orderCart).subscribe(next => {
      Swal.fire({
        text: 'Đã thêm vào giỏ hàng',
        icon: 'success',
        showConfirmButton: false,
        timer: 1400
      });
    }, error => {
      Swal.fire({
        text: 'Vui lòng đăng nhập',
        icon: 'error',
        showConfirmButton: true
      });
      this.router.navigateByUrl('/login');
    });
  }
}
