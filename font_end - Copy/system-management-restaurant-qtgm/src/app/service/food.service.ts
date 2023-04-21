import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Food} from '../model/food';

@Injectable({
  providedIn: 'root'
})
export class FoodService {

  constructor(private httpClient: HttpClient) { }
  public getListFood(idFoodType?: number,
                     name?: string,
                     page?: number): Observable<Food[]> {
    const params = {
      idFoodType,
      name,
      page
    };
    debugger
    const query = Object.entries(params)
      .filter(([_, value]) => value !== undefined && value !== null)
      .map(([key, value]) => `${key}=${value}`)
      .join('&');
    return this.httpClient.get<Food[]>(`http://localhost:8080/api/user/food/search?${query}`);
  }
}
