package com.example.system_management_restaurant_qtgm.service.impl;

import com.example.system_management_restaurant_qtgm.model.Employee;
import com.example.system_management_restaurant_qtgm.repository.IEmployeeRepository;
import com.example.system_management_restaurant_qtgm.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService implements IEmployeeService {

    @Autowired
    private IEmployeeRepository employeeRepository;
    @Override
    public Employee findEmailByUserName(String email) {
        return employeeRepository.findEmailByUserName(email);
    }
}
