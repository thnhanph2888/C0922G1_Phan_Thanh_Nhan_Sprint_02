package com.example.system_management_restaurant_qtgm.dto;

public interface IOrderDTO {
    String getId();
    String getName();
    String getActualDelivery();
    String getOrderTime();
    String getReservationTime();
    String getDescription();
    String getImage();
    Double getPrice();
    Double getRate();
    Integer getQuantity();
    Integer getTotalPrice();
    Integer getOrderDetailId();
    Integer getTable();
    Integer getSession();
    Integer getFloor();
}
