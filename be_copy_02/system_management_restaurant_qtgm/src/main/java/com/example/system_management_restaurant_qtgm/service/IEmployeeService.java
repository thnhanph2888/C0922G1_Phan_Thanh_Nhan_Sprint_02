package com.example.system_management_restaurant_qtgm.service;

import com.example.system_management_restaurant_qtgm.model.Employee;

public interface IEmployeeService {
    Employee findEmailByUserName(String username);
}
