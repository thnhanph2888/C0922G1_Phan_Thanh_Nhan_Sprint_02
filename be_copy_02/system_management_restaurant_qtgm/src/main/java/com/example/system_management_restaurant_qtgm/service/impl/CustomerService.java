package com.example.system_management_restaurant_qtgm.service.impl;

import com.example.system_management_restaurant_qtgm.dto.IDetailOrderDTO;
import com.example.system_management_restaurant_qtgm.model.Customer;
import com.example.system_management_restaurant_qtgm.repository.ICustomerRepository;
import com.example.system_management_restaurant_qtgm.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService implements ICustomerService {
    @Autowired
    private ICustomerRepository iCustomerRepository;
    @Override
    public Customer findCustomerById(Integer customerId) {
        return iCustomerRepository.findCustomerById(customerId);
    }
}
