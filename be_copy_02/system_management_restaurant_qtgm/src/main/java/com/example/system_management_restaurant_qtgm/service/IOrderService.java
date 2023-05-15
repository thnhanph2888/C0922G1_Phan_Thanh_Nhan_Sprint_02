package com.example.system_management_restaurant_qtgm.service;

import com.example.system_management_restaurant_qtgm.dto.ICartDTO;
import com.example.system_management_restaurant_qtgm.dto.IOrderDTO;
import com.example.system_management_restaurant_qtgm.dto.OrderDTO;
import com.example.system_management_restaurant_qtgm.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface IOrderService{
     Page<Order> getOrderListByIdUser(int customerId, boolean isEmployeeOrder, int status, Pageable pageable);
     Boolean updateOrderDetailQuantity(Integer quantity, Integer cartItemId);

     Integer createCartItem(String orderTime,
                            String reservationTime,
                            String actualDelivery,
                            Integer customerId,
                            Double totalPrice,
                            Boolean isEmployeeOrder,
                            Integer quantity,
                            Integer drinksId,
                            Integer foodId);
     Integer createNewOrderOnline(OrderDTO orderDTO, List<Integer> listIdCartItem);
     Boolean check(List<Integer> listIdCartItem);
     Page<ICartDTO> getCartItemByIdUser(Integer customerId,
                                        Boolean isEmployeeOrder,
                                        Pageable pageable);
     Page<IOrderDTO> getOrderListByUserId(@Param("customerId") Integer customerId,
                                             @Param("isEmployeeOrder") Boolean isEmployeeOrder,
                                             Pageable pageable);

     Page<IOrderDTO> getListNewOrder(Pageable pageable);

     Integer getTotalCartItemByUserId(@Param("customerId") Integer customerId,
                                      @Param("isEmployeeOrder") Boolean isEmployeeOrder);
     boolean deleteOrderDetailById(@Param("id") int id);
}
