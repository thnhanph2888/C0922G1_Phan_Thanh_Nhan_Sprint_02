package com.example.system_management_restaurant_qtgm.repository;

import com.example.system_management_restaurant_qtgm.dto.ICartDTO;
import com.example.system_management_restaurant_qtgm.dto.IDetailOrderDTO;
import com.example.system_management_restaurant_qtgm.dto.IOrderDTO;
import com.example.system_management_restaurant_qtgm.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Timestamp;
import java.util.List;

@Transactional
public interface IOrderRepository extends JpaRepository<Order, Integer> {

    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO `orders` \n" +
            "    (order_time, reservation_time, actual_delivery, customer_id, total_price, status, is_employee_order)\n" +
            "     VALUES \n" +
            "    (:orderTime,\n" +
            "    :reservationTime,\n" +
            "    :actualDelivery,\n" +
            "    :customerId,\n" +
            "    :totalPrice,\n" +
            "    0," +
            "    :isEmployeeOrder)")
    void createCart(
            @Param("orderTime") String orderTime,
            @Param("reservationTime") String reservationTime,
            @Param("actualDelivery") String actualDelivery,
            @Param("customerId") Integer customerId,
            @Param("totalPrice") Double totalPrice,
            @Param("isEmployeeOrder") Boolean isEmployeeOrder
    );

    @Query(nativeQuery = true, value = "SELECT LAST_INSERT_ID()")
    int getLastId();

    @Modifying
    @Query(nativeQuery = true, value = " insert into `order_detail`(quantity, drinks_id, food_id, order_id) \n " +
            " values \n " +
            " (:quantity, :drinksId, :foodId, :orderId) ")
    void createOrderDetail(@Param("quantity") Integer quantity,
                           @Param("drinksId") Integer drinksId,
                           @Param("foodId") Integer foodId,
                           @Param("orderId") Integer orderId);

    @Modifying
    @Query(nativeQuery = true, value = "update order_detail as odd \n" +
            "set odd.quantity = :quantity\n" +
            "where odd.id = :orderDetailId")
    void updateOrderDetailQuantity(@Param("quantity") Integer quantity,
                                   @Param("orderDetailId") Integer orderDetailId);


    @Query(nativeQuery = true, value = "select od.id as OrderId,f.name as Name,\n" +
            "             f.description as Description,\n" +
            "             f.image as Image,\n" +
            "             f.price as Price,\n" +
            "             f.rate as Rate,\n" +
            "             f.quantity as QuantityFood,\n" +
            "             odd.id as OrderDetailId,\n" +
            "             odd.quantity as Quantity from `orders` as od \n" +
            "             join order_detail as odd on od.id = odd.order_id\n" +
            "             join food as f on odd.food_id = f.id\n" +
            "             where od.is_employee_order = coalesce(nullif(:isEmployeeOrder, 0), od.is_employee_order)\n" +
            "             and (od.customer_id = coalesce(nullif(:customerId, 0), od.customer_id) or :customerId = 0)\n" +
            "             and od.status = 0")
    Page<ICartDTO> getCartItemPageByIdUser(@Param("customerId") Integer customerId,
                                           @Param("isEmployeeOrder") Boolean isEmployeeOrder,
                                           Pageable pageable);

    @Query(nativeQuery = true, value = "select f.id as FoodId, f.quantity as FoodQuantity, odd.quantity as QuantityOrder," +
            "             odd.id as Id" +
            "             from `orders` as od \n" +
            "             join `order_detail` as odd on od.id = odd.order_id \n" +
            "             join food as f on odd.food_id = f.id" +
            "              where od.id = :cartId")
    List<IDetailOrderDTO> getCartItemByIdCart(@Param("cartId") Integer cartId);

    @Query(nativeQuery = true, value = "select od.id as Id,f.name as Name,\n" +
            "              f.description as Description,\n" +
            "              f.image as Image,\n" +
            "              f.price as Price,\n" +
            "              f.rate as Rate,\n" +
            "              odd.quantity as Quantity \n" +
            "              from `orders` as od \n" +
            "              join order_detail as odd on od.id = odd.order_id\n" +
            "              join food as f on odd.food_id = f.id\n" +
            "              where od.status = 1 and od.employee_id is null")
    Page<IOrderDTO> getListNewOrder(Pageable pageable);

    @Query(nativeQuery = true, value = "select count(odd.id)\n" +
            "            from orders as od\n" +
            "            join order_detail as odd on od.id = odd.order_id\n" +
            "            where od.is_employee_order = coalesce(nullif(:isEmployeeOrder, 0), od.is_employee_order)\n" +
            "               and (od.customer_id = coalesce(nullif(:customerId, 0), od.customer_id) or :customerId = 0)\n" +
            "               and od.status = 0 ")
    Integer getTotalCartItemByUserId(@Param("customerId") Integer customerId,
                                     @Param("isEmployeeOrder") Boolean isEmployeeOrder);

    @Query(nativeQuery = true, value = "select od.id from orders as od\n" +
            "join order_detail as odd on od.id = odd.order_id\n" +
            "where od.is_employee_order = coalesce(nullif(:isEmployeeOrder, 0), od.is_employee_order)\n" +
            "             and (od.customer_id = coalesce(nullif(:customerId, 0), od.customer_id) or :customerId = 0)\n" +
            "             and od.status = 0")
    Integer getIdCartOfUser(@Param("customerId") Integer customerId,
                            @Param("isEmployeeOrder") Boolean isEmployeeOrder);

    @Modifying
    @Query(nativeQuery = true, value = "insert into `orders` (is_employee_order, order_time, reservation_time, status, total_price, customer_id)\n" +
            "value (:isEmployeeOrder,\n" +
            "      :orderTime,\n" +
            "      :reservationTime,\n" +
            "      1,\n" +
            "      :totalPrice,\n" +
            "      :customerId)")
    void createNewOrderOnline(@Param("isEmployeeOrder") Boolean isEmployeeOrder,
                              @Param("orderTime")Timestamp orderTime,
                              @Param("reservationTime") Timestamp reservationTime,
                              @Param("totalPrice") Double totalPrice,
                              @Param("customerId") Integer customerId);

    @Modifying
    @Query(nativeQuery = true, value = "update `order_detail` as odd\n" +
            "            set odd.order_id = :newOrderId\n " +
            "            where odd.id = :orderDetailId ")
    void moveOrderDetailToNewOrder(@Param("orderDetailId") Integer orderDetailId,
                                   @Param("newOrderId") Integer newOrderId);

    @Modifying
    @Query(nativeQuery = true, value = "update `food` as f join `order_detail` as odd on f.id = odd.food_id\n" +
            "set f.quantity = f.quantity - odd.quantity\n" +
            "where odd.id = :orderDetailId")
    void updateQuantityFood(@Param("orderDetailId") Integer orderDetailId);

    @Query(nativeQuery = true, value = "select f.name as Name,  \n" +
            "       f.image as Image,\n" +
            "       f.price as Price,\n" +
            "\t   f.rate as Rate,\n" +
            "       odd.id as OrderDetailId,\n" +
            "       odd.quantity as Quantity,\n" +
            "       od.id as Id,\n" +
            "       od.total_price as TotalPrice,\n" +
            "       od.order_time as OrderTime,\n" +
            "       od.reservation_time as ReservationTime\n" +
            "       from `orders` as od \n" +
            "       join order_detail as odd on od.id = odd.order_id\n" +
            "       join food as f on odd.food_id = f.id\n" +
            "\t     where od.is_employee_order = coalesce(nullif(:isEmployeeOrder, 0), od.is_employee_order)\n" +
            "       and (od.customer_id = coalesce(nullif(:customerId, 0), od.customer_id) or :customerId = 0)\n" +
            "       and od.status = 1")
    Page<IOrderDTO> getOrderListByUserId(@Param("customerId") Integer customerId,
                                            @Param("isEmployeeOrder") Boolean isEmployeeOrder,
                                            Pageable pageable);
    @Query(nativeQuery = true, value = "select f.quantity as FoodQuantity, odd.quantity as QuantityOrder\n" +
            "                         from `order_detail` as odd \n" +
            "                         join food as f on odd.food_id = f.id where odd.id = :orderDetailId")
    IDetailOrderDTO getOrderDetailById(Integer orderDetailId);

    @Query(nativeQuery = true, value = "select f.quantity from `food` as f where f.id = :foodId")
    Integer findQuantityFoodById(@Param("foodId") Integer foodId);

    Page<Order> findAllByCustomer_IdAndIsEmployeeOrderAndStatus(int customerId, boolean isEmployeeOrder, int status, Pageable pageable);

    @Modifying
    @Query(nativeQuery = true, value = "delete from `order_detail` as odd where odd.id = :id")
    void deleteOrderDetailById(@Param("id") int id);
}
