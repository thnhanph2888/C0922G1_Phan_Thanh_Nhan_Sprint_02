import {Customer} from './customer';
import {Employee} from './employee';
import {DinningTable} from './dinning-table';

export interface Order {
  id?: number;
  delivery?: string;
  orderTime?: string;
  reservationTime?: string;
  actualDelivery?: string;
  customer?: Customer;
  employee?: Employee;
  totalPrice?: number;
  dinningTable?: DinningTable;
}
