package com.example.system_management_restaurant_qtgm.service;

import com.example.system_management_restaurant_qtgm.model.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IFoodService {
    Page<Food> searchFood(Integer idFoodType, Double priceMin, Double priceMax, String name, Pageable pageable);
    Food findById(Integer foodId);
}
