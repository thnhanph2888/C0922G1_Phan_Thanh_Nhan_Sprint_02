package com.example.system_management_restaurant_qtgm.controller;

import com.example.system_management_restaurant_qtgm.dto.ICartDTO;
import com.example.system_management_restaurant_qtgm.dto.IOrderDTO;
import com.example.system_management_restaurant_qtgm.dto.OrderDTO;
import com.example.system_management_restaurant_qtgm.dto.OrderRequest;
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

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("api/order")
public class OrderRestController {

    @Autowired
    private IOrderService iOrderService;

    @GetMapping("/customer/setQuantityCartItem")
    public ResponseEntity<?> setQuantityCartItem(@RequestParam("quantity") Integer quantity,
                                                 @RequestParam("cartItemId") Integer cartItemId) {
       if (iOrderService.updateOrderDetailQuantity(quantity, cartItemId)) {
           return new ResponseEntity<>(HttpStatus.OK);
       } else {
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
    }


    @PostMapping("/customer/addCart")
    public ResponseEntity<?> addToCart(@Validated @RequestBody OrderDTO orderDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        boolean isCreateSuccess = iOrderService.createCartItem(
                null,
                null,
                null,
                orderDTO.getUserId(),
                orderDTO.getTotalPrice(),
                orderDTO.isEmployeeOrder(),
                orderDTO.getQuantity(),
                null,
                orderDTO.getFoodId());
        if (isCreateSuccess) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @GetMapping("/customer/getCart")
    public ResponseEntity<Page<ICartDTO>> getListCartByIdUser(@RequestParam(value = "customerId", defaultValue = "0", required = false) Integer customerId,
                                                              @RequestParam(value = "isEmployeeOrder", defaultValue = "0", required = false) Boolean isEmployeeOrder,
                                                              @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
                                                              @RequestParam(value = "size", defaultValue = "10", required = false) Integer size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(page, size, sort);
        try {
            Page<ICartDTO> orderDTOPage = iOrderService.getCartItemByIdUser(customerId, isEmployeeOrder, pageable);
            if (!orderDTOPage.hasContent()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(orderDTOPage, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/customer/getOrder")
    public ResponseEntity<Page<IOrderDTO>> getOrderListByIdUser(@RequestParam(value = "customerId", defaultValue = "0", required = false) Integer customerId,
                                                              @RequestParam(value = "isEmployeeOrder", defaultValue = "0", required = false) Boolean isEmployeeOrder,
                                                              @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
                                                              @RequestParam(value = "size", defaultValue = "10", required = false) Integer size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(page, size, sort);
        try {
            Page<IOrderDTO> orderDTOPage = iOrderService.getOrderListByUserId(customerId, isEmployeeOrder, pageable);
            if (!orderDTOPage.hasContent()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(orderDTOPage, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/customer/createNewOrder")
    public ResponseEntity<?> createNewOrder(@RequestBody OrderRequest orderRequest) {
        List<Integer> listIdCartItem = orderRequest.getListIdCartItem();
        OrderDTO order = orderRequest.getOrder();
        boolean isUpdateSuccess = iOrderService.createNewOrderOnline(order, listIdCartItem);
        if (isUpdateSuccess) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/employee/orderNewList")
    public ResponseEntity<Page<IOrderDTO>> getOrderNewList(@RequestParam(value = "page", defaultValue = "0")Integer page,
                                                           @RequestParam(value = "size", defaultValue = "10")Integer size) {
//        Sort sort = Sort.by(Sort.Direction.DESC, "");
//        Pageable pageable = PageRequest.of(page, size, sort);
        Pageable pageable = PageRequest.of(page, size);
        Page<IOrderDTO> orderDTOPage = iOrderService.getListNewOrder(pageable);
        if (orderDTOPage.hasContent()) {
            return new ResponseEntity<>(orderDTOPage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/customer/totalCart")
    public ResponseEntity<Integer> getTotalCartItem(@RequestParam(value = "customerId") Integer customerId,
                                                    @RequestParam(value = "isEmployeeOrder") Boolean isEmployeeOrder) {
        Integer totalCartItem = iOrderService.getTotalCartItemByUserId(customerId, isEmployeeOrder);
        if (totalCartItem != null) {
            return new ResponseEntity<>(totalCartItem, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
