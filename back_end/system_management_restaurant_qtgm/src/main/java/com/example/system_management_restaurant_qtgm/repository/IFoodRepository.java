package com.example.system_management_restaurant_qtgm.repository;

import com.example.system_management_restaurant_qtgm.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IFoodRepository extends JpaRepository<Food, Integer> {

}
