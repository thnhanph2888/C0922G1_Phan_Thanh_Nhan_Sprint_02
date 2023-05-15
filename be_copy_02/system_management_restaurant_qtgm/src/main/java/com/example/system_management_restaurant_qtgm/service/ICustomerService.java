package com.example.system_management_restaurant_qtgm.service;

import com.example.system_management_restaurant_qtgm.dto.IDetailOrderDTO;
import com.example.system_management_restaurant_qtgm.model.Customer;
import org.springframework.data.repository.query.Param;

public interface ICustomerService {
    Customer findCustomerById(Integer orderDetailId);
}
