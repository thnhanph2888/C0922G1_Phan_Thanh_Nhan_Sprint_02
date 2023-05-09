package com.example.system_management_restaurant_qtgm.service;

import com.example.system_management_restaurant_qtgm.dto.ICartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IOrderService{
     Boolean createOrder(String deliveryLocation,
                      String orderTime,
                      String reservationTime,
                      String actualDelivery,
                      Integer customerId,
                      Integer employeeId,
                      Double totalPrice,
                      Integer isCartItem,
                      Boolean isEmployeeOrder,
                      Integer quantity,
                      Integer drinksId,
                      Integer foodId);
     Page<ICartDTO> getCartItemByIdUser(Integer customerId,
                                        Integer employeeId,
                                        Pageable pageable);
     Boolean setCartItemToOrderItemById(Integer idCartItem);

     Page<ICartDTO> getListNewOrder(Pageable pageable);
}
