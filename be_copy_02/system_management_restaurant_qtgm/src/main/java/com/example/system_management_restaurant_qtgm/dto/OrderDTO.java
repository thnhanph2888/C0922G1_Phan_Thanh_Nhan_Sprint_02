package com.example.system_management_restaurant_qtgm.dto;

import java.sql.Timestamp;

public class OrderDTO {

    private int id;
    private String deliveryLocation;
    private Timestamp reservationTime;
    private Timestamp actualDelivery;
    private double totalPrice;
    private int cartItem;
    private int userId;
    private int foodId;
    private int drinksId;
    private int quantity;

    public OrderDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeliveryLocation() {
        return deliveryLocation;
    }

    public void setDeliveryLocation(String deliveryLocation) {
        this.deliveryLocation = deliveryLocation;
    }

    public Timestamp getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(Timestamp reservationTime) {
        this.reservationTime = reservationTime;
    }

    public Timestamp getActualDelivery() {
        return actualDelivery;
    }

    public void setActualDelivery(Timestamp actualDelivery) {
        this.actualDelivery = actualDelivery;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int isCartItem() {
        return cartItem;
    }

    public void setCartItem(int cartItem) {
        this.cartItem = cartItem;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public int getDrinksId() {
        return drinksId;
    }

    public void setDrinksId(int drinksId) {
        this.drinksId = drinksId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
