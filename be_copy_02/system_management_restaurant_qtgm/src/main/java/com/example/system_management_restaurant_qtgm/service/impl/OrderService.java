package com.example.system_management_restaurant_qtgm.service.impl;

import com.example.system_management_restaurant_qtgm.dto.ICartDTO;
import com.example.system_management_restaurant_qtgm.dto.IDetailOrderDTO;
import com.example.system_management_restaurant_qtgm.dto.IOrderDTO;
import com.example.system_management_restaurant_qtgm.dto.OrderDTO;
import com.example.system_management_restaurant_qtgm.repository.IOrderRepository;
import com.example.system_management_restaurant_qtgm.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class OrderService implements IOrderService {

    @Autowired
    private IOrderRepository iOrderRepository;

    @Override
    public Boolean updateOrderDetailQuantity(Integer quantity, Integer orderDetailId) {
        try {
            iOrderRepository.updateOrderDetailQuantity(quantity, orderDetailId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean createCartItem(String orderTime,
                                  String reservationTime,
                                  String actualDelivery,
                                  Integer customerId,
                                  Double totalPrice,
                                  Boolean isEmployeeOrder,
                                  Integer quantity,
                                  Integer drinksId,
                                  Integer foodId) {
        Integer cartId = iOrderRepository.getIdCartOfUser(customerId, isEmployeeOrder);
        boolean isHasCart = cartId != null;
        if (isHasCart) {
            List<IDetailOrderDTO> foodIdCartItemList = iOrderRepository.getFoodIdOfCart(cartId);
            if (foodIdCartItemList != null) {
                for (IDetailOrderDTO cartItem : foodIdCartItemList) {
                    if (Objects.equals(cartItem.getFoodId(), foodId)) {
                        try {
                            iOrderRepository.updateOrderDetailQuantity(cartItem.getQuantityOrder() + quantity
                                                                       , cartItem.getId());
                            return true;
                        } catch (Exception e) {
                            return false;
                        }
                    }
                }
            }
            return addNewCartItem(quantity, drinksId, cartId, foodId);
        } else {
            return createNewCart(orderTime, reservationTime, actualDelivery, customerId, totalPrice, isEmployeeOrder, quantity, drinksId, foodId);
        }
    }

    @Override
    public Boolean createNewOrderOnline(OrderDTO orderDTO, List<Integer> listIdCartItem) {
        try {
            iOrderRepository.createNewOrderOnline(orderDTO.isEmployeeOrder(),
                                                  orderDTO.getReservationTime(),
                                                  orderDTO.getTotalPrice(),
                                                   orderDTO.getUserId());
            int newOrderId = iOrderRepository.getLastId();
            for (Integer cartItemId: listIdCartItem) {
                iOrderRepository.moveOrderDetailToNewOrder(cartItemId, newOrderId);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    boolean addNewCartItem(Integer quantity,
                           Integer drinksId,
                           Integer cartId,
                           Integer foodId) {
        try {
            iOrderRepository.createOrderDetail(quantity,
                    drinksId,
                    foodId,
                    cartId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    boolean createNewCart(String orderTime,
                          String reservationTime,
                          String actualDelivery,
                          Integer customerId,
                          Double totalPrice,
                          Boolean isEmployeeOrder,
                          Integer quantity,
                          Integer drinksId,
                          Integer foodId) {
        try {
            iOrderRepository.createCart(
                    orderTime,
                    reservationTime,
                    actualDelivery,
                    customerId,
                    totalPrice,
                    isEmployeeOrder);
            iOrderRepository.createOrderDetail(quantity,
                    drinksId,
                    foodId,
                    iOrderRepository.getLastId());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Page<ICartDTO> getCartItemByIdUser(Integer customerId, Boolean isEmployeeOrder, Pageable pageable) {
        return iOrderRepository.getCartItemPageByIdUser(customerId, isEmployeeOrder, pageable);
    }

    @Override
    public Page<IOrderDTO> getOrderListByUserId(Integer customerId, Boolean isEmployeeOrder, Pageable pageable) {
        return iOrderRepository.getOrderListByUserId(customerId, isEmployeeOrder, pageable);
    }

    @Override
    public Page<IOrderDTO> getListNewOrder(Pageable pageable) {
        return iOrderRepository.getListNewOrder(pageable);
    }

    @Override
    public Integer getTotalCartItemByUserId(Integer customerId, Boolean isEmployeeOrder) {
        return iOrderRepository.getTotalCartItemByUserId(customerId, isEmployeeOrder);
    }

}
