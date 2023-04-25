package com.example.system_management_restaurant_qtgm.repository;

import com.example.system_management_restaurant_qtgm.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderRepository extends JpaRepository<Order, Integer> {
}
