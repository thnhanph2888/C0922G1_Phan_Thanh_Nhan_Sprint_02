import { Component, OnInit } from '@angular/core';
import {FoodService} from '../../../service/food.service';
import {Food} from '../../../model/food';

@Component({
  selector: 'app-food-list',
  templateUrl: './food-list.component.html',
  styleUrls: ['./food-list.component.css']
})
export class FoodListComponent implements OnInit {
  public foodList: Food[];
  public foodPages: any;
  public currentPage: number;
  public totalPage: number;
  constructor(private foodService: FoodService) { }

  ngOnInit(): void {
    this.foodService.getListFood(null, null, 0).subscribe(foodPages => {
      this.foodPages = foodPages;
      this.foodList = this.foodPages.content;
      this.totalPage = this.foodPages.totalPages;
      this.currentPage = this.foodPages.number;
    });
  }

}
