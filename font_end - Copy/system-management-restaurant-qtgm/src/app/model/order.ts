
export interface Order {
  id?: number;
  deliveryLocation?: string;
  orderTime?: string;
  reservationTime?: string;
  actualDelivery?: string;
  totalPrice?: number;
  status?: number;
  isEmployeeOrder?: boolean;
  userId?: number;
  foodId?: number;
  drinksId?: number;
  quantity?: number;
}
