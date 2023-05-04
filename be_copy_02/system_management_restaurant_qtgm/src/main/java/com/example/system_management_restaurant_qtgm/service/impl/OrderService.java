package com.example.system_management_restaurant_qtgm.service.impl;

import com.example.system_management_restaurant_qtgm.dto.IOrderDTO;
import com.example.system_management_restaurant_qtgm.repository.IOrderRepository;
import com.example.system_management_restaurant_qtgm.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderService implements IOrderService {

    @Autowired
    private IOrderRepository orderRepository;
    @Override
    public Boolean createOrder(String deliveryLocation,
                            String orderTime,
                            String reservationTime,
                            String actualDelivery,
                            Integer customerId,
                            Integer employeeId,
                            Double totalPrice,
                            Integer isCartItem,
                            Integer quantity,
                            Integer drinksId,
                            Integer foodId) {
        try {
            orderRepository.createOrder(deliveryLocation,
                    orderTime,
                    reservationTime,
                    actualDelivery,
                    customerId,
                    employeeId,
                    totalPrice,
                    isCartItem);
            orderRepository.createOrderDetail(quantity,
                    drinksId,
                    foodId,
                    orderRepository.getLastId());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Page<IOrderDTO> getCartItemForCustomerById(Integer customerId, Integer employeeId, Pageable pageable) {
        return orderRepository.getCartItemForCustomerById(customerId, employeeId, pageable);
    }
}
