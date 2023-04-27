import {Component, OnInit} from '@angular/core';
import {FoodService} from '../../../service/food.service';
import {Food} from '../../../model/food';
import {FoodType} from '../../../model/food-type';

@Component({
  selector: 'app-food-list',
  templateUrl: './food-list.component.html',
  styleUrls: ['./food-list.component.css']
})
export class FoodListComponent implements OnInit {
  public foodList: Food[];
  public foodTypeList: FoodType[];
  public foodPages: any;
  public currentPage: number;
  public totalPage: number;
  public foodTypeIdSearch: number = null;
  public foodNameSearch: string = null;
  public foodPriceMinSearch: number = null;
  public foodPriceMaxSearch: number;
  public isOrder = false;
  constructor(private foodService: FoodService) {
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
}
