package com.example.system_management_restaurant_qtgm.controller;

import com.example.system_management_restaurant_qtgm.model.Food;
import com.example.system_management_restaurant_qtgm.model.FoodType;
import com.example.system_management_restaurant_qtgm.service.IFoodService;
import com.example.system_management_restaurant_qtgm.service.IFoodTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("api/food")
public class FoodRestController {

    @Autowired
    private IFoodService iFoodService;
    @Autowired
    private IFoodTypeService foodTypeService;

    @GetMapping("/search")
    public ResponseEntity<Page<Food>> searchFood(@RequestParam(value = "idFoodType", defaultValue = "0") Integer idFoodType,
                                                 @RequestParam(value = "priceMin", defaultValue = "0") Double priceMin,
                                                 @RequestParam(value = "priceMax", defaultValue = "0") Double priceMax,
                                                 @RequestParam(value = "name", defaultValue = "") String name,
                                                 @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                 @RequestParam(value = "size", defaultValue = "12") Integer size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "price", "rate");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Food> foodList = iFoodService.searchFood(idFoodType, priceMin, priceMax, name, pageable);
        if (foodList == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(foodList, HttpStatus.OK);
        }
    }

    @GetMapping("/foodType")
    public ResponseEntity<List<FoodType>> findAllFoodType() {
        List<FoodType> foodTypeList = foodTypeService.findAllFoodType();
        if (foodTypeList != null) {
            return new ResponseEntity<>(foodTypeList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Food> findFoodById(@PathVariable("id") Integer foodId) {
        Food food = iFoodService.findById(foodId);
        if (food != null) {
            return new ResponseEntity<>(food, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
