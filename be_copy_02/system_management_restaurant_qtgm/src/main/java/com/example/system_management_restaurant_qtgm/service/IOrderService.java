package com.example.system_management_restaurant_qtgm.service;

import com.example.system_management_restaurant_qtgm.dto.IOrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

public interface IOrderService{
     Boolean createOrder(String deliveryLocation,
                      String orderTime,
                      String reservationTime,
                      String actualDelivery,
                      Integer customerId,
                      Integer employeeId,
                      Double totalPrice,
                      Integer isCartItem,
                      Integer quantity,
                      Integer drinksId,
                      Integer foodId);
     Page<IOrderDTO> getCartItemForCustomerById(Integer customerId,
                                         Integer employeeId,
                                         Pageable pageable);
}
