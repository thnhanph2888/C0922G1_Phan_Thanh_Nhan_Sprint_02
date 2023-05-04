package com.example.system_management_restaurant_qtgm.controller;

import com.example.system_management_restaurant_qtgm.dto.IOrderDTO;
import com.example.system_management_restaurant_qtgm.dto.OrderDTO;
import com.example.system_management_restaurant_qtgm.repository.IOrderRepository;
import com.example.system_management_restaurant_qtgm.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
@RestController
@CrossOrigin("*")
@RequestMapping("api/order")
public class OrderRestController {

    @Autowired
    private IOrderService iOrderService;
    @PostMapping("/employee/order")
    public ResponseEntity<?> createOrder(@Validated @RequestBody OrderDTO orderDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        if (orderDTO.isCartItem() == 1) {
           if (iOrderService.createOrder(orderDTO.getDeliveryLocation(),
                    null,
                    null,
                    null,
                    orderDTO.getUserId(),
                    null,
                    orderDTO.getTotalPrice(),
                    orderDTO.isCartItem(),
                    orderDTO.getQuantity(),
                    null,
                    orderDTO.getFoodId())) {
               return new ResponseEntity<>(HttpStatus.OK);
           } else {
               return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
           }
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/customer/list/customer")
    public ResponseEntity<Page<IOrderDTO>> getCartItemForCustomerById(@RequestParam(value = "customerId",defaultValue = "0", required = false) Integer customerId,
                                                                      @RequestParam(value = "employeeId",defaultValue = "0", required = false) Integer employeeId,
                                                                      @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
                                                                      @RequestParam(value = "size", defaultValue = "10", required = false) Integer size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(page, size, sort);
        if (customerId == 0 && employeeId == 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            Page<IOrderDTO> orderDTOPage = iOrderService.getCartItemForCustomerById(customerId, employeeId, pageable);
            if (orderDTOPage == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(orderDTOPage, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
