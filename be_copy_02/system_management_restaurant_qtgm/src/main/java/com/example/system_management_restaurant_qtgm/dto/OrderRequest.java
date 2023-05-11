package com.example.system_management_restaurant_qtgm.dto;

import java.util.List;

public class OrderRequest {
    private List<Integer> listIdCartItem;
    private OrderDTO order;

    public OrderRequest() {
    }

    public List<Integer> getListIdCartItem() {
        return listIdCartItem;
    }

    public void setListIdCartItem(List<Integer> listIdCartItem) {
        this.listIdCartItem = listIdCartItem;
    }

    public OrderDTO getOrder() {
        return order;
    }

    public void setOrder(OrderDTO order) {
        this.order = order;
    }
}
