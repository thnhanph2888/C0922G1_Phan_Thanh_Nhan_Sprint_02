import {FoodType} from './food-type';

export interface Food {
   id?: number;
   image?: string;
   name?: string;
   price?: number;
   rate?: number;
   description?: string;
   foodType?: FoodType;
   quantity?: number;
}
