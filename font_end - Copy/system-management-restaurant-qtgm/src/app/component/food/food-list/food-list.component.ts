import {Component, OnInit} from '@angular/core';
import {FoodService} from '../../../service/food.service';
import {Food} from '../../../model/food';
import {FoodType} from '../../../model/food-type';
import {Order} from '../../../model/order';
import {TokenStorageService} from '../../security-authentication/service/token-storage.service';
import {OrderService} from '../../../service/order.service';
import Swal from 'sweetalert2';
import {Router} from '@angular/router';

@Component({
  selector: 'app-food-list',
  templateUrl: './food-list.component.html',
  styleUrls: ['./food-list.component.css']
})
export class FoodListComponent implements OnInit {
  foodList: Food[];
  foodTypeList: FoodType[];
  foodPages: any;
  currentPage: number;
  totalPage: number;
  foodTypeIdSearch: number = null;
  foodNameSearch: string = null;
  foodPriceMinSearch: number = null;
  foodPriceMaxSearch: number;
  isOrder = false;
  foodCartId: number;
  foodCartName: string;
  foodCartImage: string;
  foodCartRate: number;
  foodCartPrice: number;
  foodCartQuantity = 1;
  size = 12;
  isLoggedIn = false;

  constructor(private foodService: FoodService,
              private tokenStorageService: TokenStorageService,
              private orderService: OrderService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.checkLogin();
    this.findAllFoodType();
    this.searchFood();
  }

  checkLogin() {
    this.isLoggedIn = this.tokenStorageService.getToken() != null;
  }

  setValueSearchCategory(foodTypeId: number) {
    this.foodTypeIdSearch = foodTypeId;
    this.searchFood();
  }

  findAllFoodType() {
    this.foodService.findAllFoodType().subscribe(foodTypeList => {
      this.foodTypeList = foodTypeList;
    });
  }

  setPriceSearch() {
    this.foodPriceMaxSearch = +this.foodPriceMinSearch + 49999;
  }

  searchFood() {
    if (this.foodPriceMinSearch != null) {
      this.setPriceSearch();
    }
    this.foodService.findListFood(
      this.foodTypeIdSearch,
      this.foodNameSearch,
      this.foodPriceMinSearch,
      this.foodPriceMaxSearch,
      0,
      this.size).subscribe(foodPages => {
      this.foodPages = foodPages;
      this.foodList = this.foodPages.content;
      this.totalPage = this.foodPages.totalPages;
      this.currentPage = this.foodPages.number;
    });
  }

  reset() {
    this.foodTypeIdSearch = null;
    this.foodNameSearch = null;
    this.foodPriceMinSearch = null;
    this.foodPriceMaxSearch = null;
    this.size = 12;
    this.searchFood();
  }

  openOrder() {
    this.isOrder = true;
  }

  closeOrder() {
    this.isOrder = false;
  }

  setInfoModalAddToCart(foodCartId: number,
                        foodCartName: string,
                        foodCartImage: string,
                        foodCartRate: number,
                        foodCartPrice: number) {
    this.foodCartImage = foodCartImage;
    this.foodCartId = foodCartId;
    this.foodCartName = foodCartName;
    this.foodCartRate = foodCartRate;
    this.foodCartPrice = foodCartPrice;
  }

  addToCart() {
    let isEmployeeOrder = false;
    if (this.tokenStorageService.getRole() === 'ROLE_EMPLOYEE') {
      isEmployeeOrder = true;
    }
    const orderCart: Order = {
      userId: this.tokenStorageService.getUser().userId,
      foodId: this.foodCartId,
      quantity: this.foodCartQuantity,
      status: 0,
      isEmployeeOrder
    };
    this.orderService.addCart(orderCart).subscribe(next => {
      this.foodCartQuantity = 1;
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

  decrease() {
    this.foodCartQuantity--;
  }

  increase() {
    this.foodCartQuantity++;
  }

  resetQuantity() {
    this.foodCartQuantity = 1;
  }

  loadMore() {
    this.size += 12;
    this.searchFood();
  }
}
