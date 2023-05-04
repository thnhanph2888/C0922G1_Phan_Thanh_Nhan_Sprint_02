package com.example.system_management_restaurant_qtgm.repository;

import com.example.system_management_restaurant_qtgm.model.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IFoodRepository extends JpaRepository<Food, Integer> {

    @Query(nativeQuery = true, value = "select f.* \n" +
            "from `food` as f\n" +
            "where f.food_type_id = coalesce(nullif(:idFoodType,0), f.food_type_id)\n" +
            "and f.price >= coalesce(nullif(:priceMin,0), f.price)\n" +
            "and f.price <= coalesce(nullif(:priceMax,0), f.price)\n" +
            "and f.name like concat('%',:name,'%')")
    Page<Food> searchFood(@Param("idFoodType") int idFoodType,
                          @Param("priceMin") double priceMin,
                          @Param("priceMax") double priceMax,
                          @Param("name") String name,
                          Pageable pageable);
}
