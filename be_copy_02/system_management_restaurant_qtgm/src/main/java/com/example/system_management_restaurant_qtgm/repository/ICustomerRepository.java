package com.example.system_management_restaurant_qtgm.repository;

import com.example.system_management_restaurant_qtgm.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ICustomerRepository  extends JpaRepository<Customer, Integer> {
    @Query(nativeQuery = true, value = "select cus.* from `customer` as cus where cus.id = :customerId")
    Customer findCustomerById(@Param("customerId") Integer orderDetailId);
}
