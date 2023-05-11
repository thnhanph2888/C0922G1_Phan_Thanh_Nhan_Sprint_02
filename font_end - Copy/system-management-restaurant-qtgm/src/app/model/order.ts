
export interface Order {
  id?: number;
  deliveryLocation?: string;
  orderTime?: string;
  reservationTime?: string;
  actualDelivery?: string;
  totalPrice?: number;
  status?: number;
  employeeOrder?: boolean;
  userId?: number;
  foodId?: number;
  drinksId?: number;
  quantity?: number;
  name?: string;
  image?: string;
  price?: number;
  rate?: number;
  orderDetailId?: number;
  table?: number;
  session?: number;
  floor?: number;
}
