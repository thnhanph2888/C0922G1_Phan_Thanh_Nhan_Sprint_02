package com.example.system_management_restaurant_qtgm.service.impl;

import com.example.system_management_restaurant_qtgm.repository.IOrderRepository;
import com.example.system_management_restaurant_qtgm.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private IOrderRepository orderRepository;
    @Override
    public void createOrder(String address, String orderTime, String reservationTime, String actualDelivery, String customerId, String employeeId, String totalPrice, String isCartItem) {
        orderRepository.createOrder(address, orderTime, reservationTime, actualDelivery, customerId, employeeId, totalPrice, isCartItem);
    }
}
