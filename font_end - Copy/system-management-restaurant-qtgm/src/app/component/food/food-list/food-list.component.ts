import {Component, OnInit} from '@angular/core';
import {FoodService} from '../../../service/food.service';
import {Food} from '../../../model/food';
import {FoodType} from '../../../model/food-type';
import {Order} from '../../../model/order';
import {TokenStorageService} from '../../security-authentication/service/token-storage.service';
import {OrderService} from '../../../service/order.service';
import Swal from 'sweetalert2';
import {Router} from '@angular/router';
import {ShareService} from '../../security-authentication/service/share.service';

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
  foodQuantity: number;
  size = 12;
  isLoggedIn = false;
  hasNext: boolean;
  isAddCartSuccess: boolean;
  constructor(private foodService: FoodService,
              private tokenStorageService: TokenStorageService,
              private orderService: OrderService,
              private router: Router,
              private shareService: ShareService) {
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
      this.setNewPageFood(foodPages);
    });
  }

  setNewPageFood(foodPage: any) {
    this.foodPages = foodPage;
    this.hasNext = this.foodPages.number + 1 < this.foodPages.totalPages;
    this.foodList = this.foodPages.content;
    debugger
    this.totalPage = this.foodPages.totalPages;
    this.currentPage = this.foodPages.number;
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

  setInfoModalAddToCart(food: Food) {
    this.foodCartImage = food.image;
    this.foodCartId = food.id;
    this.foodCartName = food.name;
    this.foodCartRate = food.rate;
    this.foodCartPrice = food.price;
  }

  addToCart() {
    const orderCart = this.getObjectOrder();
    this.orderService.addCart(orderCart).subscribe(status => {
      if (status === -1) {
        this.foodCartQuantity = 1;
        this.shareService.sendClickEvent();
        Swal.fire({
          text: 'Đã thêm vào giỏ hàng!',
          icon: 'success',
          showConfirmButton: true,
          timer: 1400
        });
        this.isAddCartSuccess = true;
      } else if (status > 0) {
        this.foodCartQuantity = status;
        Swal.fire({
          text: 'Xin lỗi hiện tại chúng tôi không có đủ số lượng yêu cầu, số lượng có thể thêm ' + status + ' !',
          icon: 'error',
          showConfirmButton: true
        });
        this.isAddCartSuccess = false;
      } else {
        Swal.fire({
          text: 'xin lỗi quý khách món này tạm hết hôm nay vui lòng chọn món khác!',
          icon: 'error',
          showConfirmButton: true
        });
      }
    }, error => {
      if (this.tokenStorageService.getToken()) {
        Swal.fire({
          text: 'Thêm thất bại, vui lòng thử lại sau!',
          icon: 'error',
          showConfirmButton: true
        });
        this.isAddCartSuccess = false;
      } else {
        Swal.fire({
          text: 'Vui lòng đăng nhập!',
          icon: 'error',
          showConfirmButton: true
        });
        this.router.navigateByUrl('/login');
      }
    });
  }

  getObjectOrder(): Order {
    let employeeOrder = false;
    if (this.tokenStorageService.getRole() === 'ROLE_EMPLOYEE') {
      employeeOrder = true;
    }
    return {
      userId: this.tokenStorageService.getUser().userId,
      foodId: this.foodCartId,
      quantity: this.foodCartQuantity,
      status: 0,
      employeeOrder
    };
  }

  decrease() {
    if (this.foodCartQuantity <= 1) {
      Swal.fire({
        text: 'Vui lòng chọn số lượng lớn hơn 1!',
        icon: 'error',
        showConfirmButton: true
      });
    } else {
      this.foodCartQuantity--;
    }
  }

  increase() {
    if (this.foodCartQuantity >= this.foodQuantity) {
      Swal.fire({
        text: 'Xin lỗi nhà hàng chúng tôi không còn đủ số lượng, vui lòng xem món khác!',
        icon: 'error',
        showConfirmButton: true
      });
      this.foodCartQuantity = this.foodQuantity;
    }
    this.foodCartQuantity++;
  }

  resetQuantity() {
    this.foodCartQuantity = 1;
    this.hiddenClose();
  }

  loadMore() {
    this.size += 12;
    this.searchFood();
  }

  hiddenClose() {
    this.isAddCartSuccess = false;
  }
}
