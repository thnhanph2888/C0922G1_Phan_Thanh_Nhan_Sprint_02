package com.example.system_management_restaurant_qtgm.repository;

import com.example.system_management_restaurant_qtgm.dto.IOrderDTO;
import com.example.system_management_restaurant_qtgm.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface IOrderRepository extends JpaRepository<Order, Integer> {

    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO `orders` \n" +
            "    (delivery_location, order_time, reservation_time, actual_delivery, customer_id, employee_id, total_price, is_cart_item)\n" +
            "     VALUES \n" +
            "    (:address,\n" +
            "    :orderTime,\n" +
            "    :reservationTime,\n" +
            "    :actualDelivery,\n" +
            "    COALESCE(:customerId, NULL),\n" +
            "    COALESCE(:employeeId, NULL),\n" +
            "    :totalPrice,\n" +
            "    :isCartItem)")
     void createOrder(@Param("address") String deliveryLocation,
                            @Param("orderTime") String orderTime,
                            @Param("reservationTime") String reservationTime,
                            @Param("actualDelivery") String actualDelivery,
                            @Param("customerId") Integer customerId,
                            @Param("employeeId") Integer employeeId,
                            @Param("totalPrice") Double totalPrice,
                            @Param("isCartItem") Integer isCartItem
                            );

     @Query(nativeQuery = true, value = "SELECT LAST_INSERT_ID()")
     int getLastId();

     @Modifying
     @Query(nativeQuery = true, value = " insert into `order_detail`(quantity, drinks_id, food_id, order_id) \n " +
             " values \n " +
             " (:quantity, :drinksId, :foodId, :orderId) ")
     void createOrderDetail(@Param("quantity") int quantity,
                            @Param("drinksId") Integer drinksId,
                            @Param("foodId") Integer foodId,
                            @Param("orderId") Integer orderId);

     @Query(nativeQuery = true, value = "select od.id as Id,f.name as Name,\n" +
             " f.description as Description,\n" +
             " f.image as Image,\n" +
             " f.price as Price,\n" +
             " f.rate as Rate,\n" +
             " odd.quantity as Quantity from `orders` as od \n" +
             " join order_detail as odd on od.id = odd.order_id\n" +
             " join food as f on odd.food_id = f.id\n" +
             " where od.customer_id = coalesce(nullif(:customerId, 0), od.customer_id)\n" +
             " and od.employee_id = coalesce(nullif(:employeeId, 0), od.employee_id)\n" +
             " and od.is_cart_item = 1")
     Page<IOrderDTO> getCartItemForCustomerById(@Param("customerId") Integer customerId,
                                         @Param("employeeId") Integer employeeId,
                                         Pageable pageable);
}
