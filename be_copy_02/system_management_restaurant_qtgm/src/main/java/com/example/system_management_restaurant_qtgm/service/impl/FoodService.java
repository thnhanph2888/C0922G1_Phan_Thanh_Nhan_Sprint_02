package com.example.system_management_restaurant_qtgm.service.impl;

import com.example.system_management_restaurant_qtgm.model.Food;
import com.example.system_management_restaurant_qtgm.repository.IFoodRepository;
import com.example.system_management_restaurant_qtgm.service.IFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FoodService implements IFoodService {

    @Autowired
    private IFoodRepository foodRepository;

    @Override
    public Page<Food> searchFood(Integer idFoodType, Double priceMin, Double priceMax, String name, Pageable pageable) {
        return foodRepository.searchFood(idFoodType, priceMin, priceMax, name, pageable);
    }

    @Override
    public Food findById(Integer foodId) {
        return foodRepository.findById(foodId).orElse(null);
    }
}
