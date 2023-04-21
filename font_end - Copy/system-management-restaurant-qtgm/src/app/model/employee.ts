import {Account} from './account';
import {Position} from './position';

export interface Employee {
  id?: number;
  name?: string;
  date_of_birth?: string;
  gender?: boolean;
  email?: string;
  phone_number?: string;
  address?: string;
  position?: Position;
  account?: Account;
}
