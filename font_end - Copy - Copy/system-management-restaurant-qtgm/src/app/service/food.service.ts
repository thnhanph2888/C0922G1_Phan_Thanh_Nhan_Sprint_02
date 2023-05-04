import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Food} from '../model/food';
import {FoodType} from '../model/food-type';

@Injectable({
  providedIn: 'root'
})
export class FoodService {

  constructor(private httpClient: HttpClient) {
  }

  public findListFood(idFoodType?: number,
                      name?: string,
                      priceMin?: number,
                      priceMax?: number,
                      page?: number): Observable<Food[]> {
    const params = {
      idFoodType,
      name,
      priceMin,
      priceMax,
      page
    };
    const query = Object.entries(params)
      .filter(([_, value]) => value !== undefined && value !== null)
      .map(([key, value]) =>`${key}=${value}`)
      .join('&');
    debugger
    return this.httpClient.get<Food[]>(`http://localhost:8080/api/food/search?${query}`);
  }

  public findAllFoodType(): Observable<FoodType[]> {
    return this.httpClient.get<FoodType[]>(`http://localhost:8080/api/food/foodType`);
  }
}
