package com.example.system_management_restaurant_qtgm.repository;

import com.example.system_management_restaurant_qtgm.model.FoodType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IFoodTypeRepository extends JpaRepository<FoodType, Integer> {

    @Query(nativeQuery = true, value = "select ft.* from `food_type` as ft")
    List<FoodType> findAllFoodType();
}
