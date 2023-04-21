import {Account} from './account';

export interface Customer {
  id?: number;
  name?: string;
  date_of_birth?: string;
  gender?: boolean;
  email?: string;
  phone_number?: string;
  address?: string;
  account?: Account;
}
