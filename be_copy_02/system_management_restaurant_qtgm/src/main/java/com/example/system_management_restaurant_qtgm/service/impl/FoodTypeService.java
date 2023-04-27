package com.example.system_management_restaurant_qtgm.service.impl;

import com.example.system_management_restaurant_qtgm.model.FoodType;
import com.example.system_management_restaurant_qtgm.repository.IFoodTypeRepository;
import com.example.system_management_restaurant_qtgm.service.IFoodTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodTypeService implements IFoodTypeService {

    @Autowired
    private IFoodTypeRepository foodTypeRepository;
    @Override
    public List<FoodType> findAllFoodType() {
        return foodTypeRepository.findAllFoodType();
    }
}
