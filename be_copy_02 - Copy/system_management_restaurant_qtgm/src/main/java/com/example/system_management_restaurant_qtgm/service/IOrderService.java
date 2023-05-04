package com.example.system_management_restaurant_qtgm.service;

import org.springframework.data.repository.query.Param;

public interface IOrderService{
    public void createOrder(String address,
                            String orderTime,
                            String reservationTime,
                            String actualDelivery,
                            String customerId,
                            String employeeId,
                            String totalPrice,
                            String isCartItem
                             );
}
