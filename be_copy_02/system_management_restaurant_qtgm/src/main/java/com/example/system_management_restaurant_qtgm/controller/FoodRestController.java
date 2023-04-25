package com.example.system_management_restaurant_qtgm.controller;

import com.example.system_management_restaurant_qtgm.model.Food;
import com.example.system_management_restaurant_qtgm.service.IFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("api/user/food")
public class FoodRestController {

    @Autowired
    private IFoodService foodService;

    @GetMapping("/search")
    public ResponseEntity<Page<Food>> searchFood(@RequestParam(value = "idFoodType", defaultValue = "0") Integer idFoodType,
                                                 @RequestParam(value = "price", defaultValue = "0") Double price,
                                                 @RequestParam(value = "name", defaultValue = "") String name,
                                                 @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                 @RequestParam(value = "size", defaultValue = "8") Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "price");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Food> foodList = foodService.searchFood(idFoodType, price, name, pageable);
        if (foodList == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(foodList, HttpStatus.OK);
        }
    }
}
