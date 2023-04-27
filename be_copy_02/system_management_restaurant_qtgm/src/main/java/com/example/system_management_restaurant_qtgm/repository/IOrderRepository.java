package com.example.system_management_restaurant_qtgm.repository;

import com.example.system_management_restaurant_qtgm.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IOrderRepository extends JpaRepository<Order, Integer> {

    @Query(nativeQuery = true, value = "INSERT INTO `orders` \n" +
            "    (delivery_location, order_time, reservation_time, actual_delivery, customer_id, employee_id, total_price, is_cart_item)\n" +
            "     VALUES \n" +
            "    (:address,\n" +
            "    :orderTime,\n" +
            "    :reservationTime,\n" +
            "    :actualDelivery,\n" +
            "    :customerId,\n" +
            "    :employeeId,\n" +
            "    :totalPrice,\n" +
            "    :isCartItem)")
    public void createOrder(@Param("address") String address,
                            @Param("orderTime") String orderTime,
                            @Param("reservationTime") String reservationTime,
                            @Param("actualDelivery") String actualDelivery,
                            @Param("customerId") String customerId,
                            @Param("employeeId") String employeeId,
                            @Param("totalPrice") String totalPrice,
                            @Param("isCartItem") String isCartItem
                            );

//    @Query(nativeQuery = true, value = "")
}
