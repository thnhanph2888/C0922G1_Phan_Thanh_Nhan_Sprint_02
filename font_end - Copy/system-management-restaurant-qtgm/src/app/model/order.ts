
export interface Order {
  id?: number;
  delivery?: string;
  orderTime?: string;
  reservationTime?: string;
  actualDelivery?: string;
  totalPrice?: number;
  cartItem?: number;
  userId?: number;
  foodId?: number;
  drinksId?: number;
  quantity?: number;
}
