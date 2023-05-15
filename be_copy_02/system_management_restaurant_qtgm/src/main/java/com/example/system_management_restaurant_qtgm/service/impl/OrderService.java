package com.example.system_management_restaurant_qtgm.service.impl;

import com.example.system_management_restaurant_qtgm.dto.*;
import com.example.system_management_restaurant_qtgm.model.Order;
import com.example.system_management_restaurant_qtgm.repository.IOrderRepository;
import com.example.system_management_restaurant_qtgm.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
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
    public Integer createCartItem(String orderTime,
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
        int quantityReset = checkQuantityFoodById(foodId, quantity);
        if (quantityReset != -1) {
            return quantityReset;
        }
        if (isHasCart) {
            List<IDetailOrderDTO> cartItemList = iOrderRepository.getCartItemByIdCart(cartId);
            if (cartItemList != null) {
                for (IDetailOrderDTO cartItem : cartItemList) {
                    if (Objects.equals(cartItem.getFoodId(), foodId)) {
                        try {
                            if (cartItem.getQuantityOrder() + quantity > cartItem.getFoodQuantity()) {
                                return cartItem.getFoodQuantity() - cartItem.getQuantityOrder();
                            }
                            iOrderRepository.updateOrderDetailQuantity(cartItem.getQuantityOrder() + quantity
                                    , cartItem.getId());
                            return -1;
                        } catch (Exception e) {
                            return -2;
                        }
                    }
                }
            }
            return addNewCartItem(quantity, drinksId, cartId, foodId);
        } else {
            return createNewCart(orderTime, reservationTime, actualDelivery, customerId, totalPrice, isEmployeeOrder, quantity, drinksId, foodId);
        }
    }

    int checkQuantityFoodById(int id, int quantityOrder) {
        int foodQuantity = iOrderRepository.findQuantityFoodById(id);
        if (quantityOrder > foodQuantity) {
            return foodQuantity;
        }
        return -1;
    }

    @Override
    public Integer createNewOrderOnline(OrderDTO orderDTO, List<Integer> listIdCartItem) {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        try {
            iOrderRepository.createNewOrderOnline(orderDTO.isEmployeeOrder(),
                    currentTimestamp,
                    orderDTO.getReservationTime(),
                    orderDTO.getTotalPrice(),
                    orderDTO.getUserId());
            int newOrderId = iOrderRepository.getLastId();
            for (Integer cartItemId : listIdCartItem) {
                if (checkFoodQuantity(cartItemId)) {
                    iOrderRepository.updateQuantityFood(cartItemId);
                    iOrderRepository.moveOrderDetailToNewOrder(cartItemId, newOrderId);
                } else {
                    return 1;
                }
            }
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }

    public Boolean check(List<Integer> listIdCartItem) {
        for (Integer cartItemId : listIdCartItem) {
            if (!checkFoodQuantity(cartItemId)) {
                return false;
            }
        }
        return true;
    }

    boolean checkFoodQuantity(int cartItemId) {
        IDetailOrderDTO detailOrder = iOrderRepository.getOrderDetailById(cartItemId);
        return detailOrder.getQuantityOrder() <= detailOrder.getFoodQuantity();
    }

    int addNewCartItem(Integer quantity,
                       Integer drinksId,
                       Integer cartId,
                       Integer foodId) {
        try {
            iOrderRepository.createOrderDetail(quantity,
                    drinksId,
                    foodId,
                    cartId);
            return -1;
        } catch (Exception e) {
            return -2;
        }
    }

    int createNewCart(String orderTime,
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
            return -1;
        } catch (Exception e) {
            return -2;
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

    public Page<Order> getOrderListByIdUser(int customerId, boolean isEmployeeOrder, int status, Pageable pageable) {
         return iOrderRepository.findAllByCustomer_IdAndIsEmployeeOrderAndStatus(customerId, isEmployeeOrder, status, pageable);
    }

    @Override
    public boolean deleteOrderDetailById(int id) {
        try {
            iOrderRepository.deleteOrderDetailById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
