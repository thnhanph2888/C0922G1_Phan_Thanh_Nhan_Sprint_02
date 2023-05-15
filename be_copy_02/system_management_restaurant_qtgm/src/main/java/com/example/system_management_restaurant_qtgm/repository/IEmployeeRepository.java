package com.example.system_management_restaurant_qtgm.repository;

import com.example.system_management_restaurant_qtgm.model.Customer;
import com.example.system_management_restaurant_qtgm.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IEmployeeRepository extends JpaRepository<Employee, Integer> {

    @Query(nativeQuery = true, value = "select em.* from employee as em\n" +
            "join account as acc on em.account_id = acc.id\n" +
            "where acc.username = :username")
    Employee findEmailByUserName(@Param("username") String email);

    @Query(nativeQuery = true, value = "select em.* from `employee` as em where cus.id = :employeeId")
    Customer findEmployeeById(@Param("employeeId") Integer employeeId);
}
