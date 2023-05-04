import {Component, OnInit} from '@angular/core';
import {FoodService} from '../../../service/food.service';
import {Food} from '../../../model/food';
import {FoodType} from '../../../model/food-type';
import {Order} from '../../../model/order';
import {TokenStorageService} from '../../security-authentication/service/token-storage.service';
import {OrderService} from '../../../service/order.service';
import Swal from 'sweetalert2';

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
  orderCart: Order;
  foodCartQuantity: number;
  constructor(private foodService: FoodService,
              private tokenStorageService: TokenStorageService,
              private orderService: OrderService) {
  }

  ngOnInit(): void {
    this.findAllFoodType();
    this.searchFood();
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
      0).subscribe(foodPages => {
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
    this.orderCart = {
      userId: this.tokenStorageService.getUser().userId,
      foodId: this.foodCartId,
      quantity: this.foodCartQuantity,
      cartItem: 1
    };
    this.orderService.addCart(this.orderCart).subscribe(next => {
      Swal.fire({
        text: 'Đã thêm vào giỏ hàng',
        icon: 'success',
        showConfirmButton: false,
        timer: 1500
      });
    });
  }
}
