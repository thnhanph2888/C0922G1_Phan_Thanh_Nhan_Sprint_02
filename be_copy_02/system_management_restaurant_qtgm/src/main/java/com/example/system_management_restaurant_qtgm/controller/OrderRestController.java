package com.example.system_management_restaurant_qtgm.controller;

import com.example.system_management_restaurant_qtgm.dto.ICartDTO;
import com.example.system_management_restaurant_qtgm.dto.OrderDTO;
import com.example.system_management_restaurant_qtgm.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("api/order")
public class OrderRestController {

    @Autowired
    private IOrderService iOrderService;

    @PostMapping("/customer/addCart")
    public ResponseEntity<?> addToCart(@Validated @RequestBody OrderDTO orderDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        if (iOrderService.createOrder(orderDTO.getDeliveryLocation(),
                null,
                null,
                null,
                orderDTO.getUserId(),
                null,
                orderDTO.getTotalPrice(),
                orderDTO.getStatus(),
                orderDTO.isEmployeeOrder(),
                orderDTO.getQuantity(),
                null,
                orderDTO.getFoodId())) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/customer/cart")
    public ResponseEntity<Page<ICartDTO>> getListCartByIdUser(@RequestParam(value = "customerId", defaultValue = "0", required = false) Integer customerId,
                                                              @RequestParam(value = "employeeId", defaultValue = "0", required = false) Integer employeeId,
                                                              @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
                                                              @RequestParam(value = "size", defaultValue = "10", required = false) Integer size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(page, size, sort);
        if (customerId == 0 && employeeId == 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            Page<ICartDTO> orderDTOPage = iOrderService.getCartItemByIdUser(customerId, employeeId, pageable);
            if (!orderDTOPage.hasContent()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(orderDTOPage, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @Transactional
    @PostMapping("/customer/setCartToOrder")
    public ResponseEntity<?> setCartToOrder(@RequestBody List<Integer> listIdCartItem) {
        for (Integer integer : listIdCartItem) {
            if (!this.iOrderService.setCartItemToOrderItemById(integer)) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/employee/orderNewList")
    public ResponseEntity<Page<ICartDTO>> getOrderNewList(@RequestParam(value = "page", defaultValue = "0")Integer page,
                                                          @RequestParam(value = "size", defaultValue = "10")Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "");
        Page<ICartDTO> orderDTOPage = iOrderService.getListNewOrder();
    }
}
