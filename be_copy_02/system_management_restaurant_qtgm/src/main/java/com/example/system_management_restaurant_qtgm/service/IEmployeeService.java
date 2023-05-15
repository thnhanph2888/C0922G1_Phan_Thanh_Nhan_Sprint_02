package com.example.system_management_restaurant_qtgm.service;

import com.example.system_management_restaurant_qtgm.model.Customer;
import com.example.system_management_restaurant_qtgm.model.Employee;
import org.springframework.data.repository.query.Param;

public interface IEmployeeService {
    Employee findEmailByUserName(String username);
    Customer findEmployeeById(@Param("employeeId") Integer employeeId);
}
