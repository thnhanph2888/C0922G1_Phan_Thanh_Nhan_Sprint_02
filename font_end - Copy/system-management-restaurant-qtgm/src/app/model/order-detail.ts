import {Food} from './food';
import {Drinks} from './drinks';

export interface OrderDetail {
  id?: number;
  food?: Food;
  drinks?: Drinks;
  quantity?: number;
}
