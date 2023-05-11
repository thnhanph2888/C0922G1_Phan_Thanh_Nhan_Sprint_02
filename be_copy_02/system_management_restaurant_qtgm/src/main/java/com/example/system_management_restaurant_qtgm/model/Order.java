package com.example.system_management_restaurant_qtgm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Timestamp orderTime;
    private Timestamp reservationTime;
    private Timestamp actualDelivery;
    private double totalPrice;
    private int status;
    private boolean isEmployeeOrder;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;
    @JsonIgnore
    @OneToMany(mappedBy = "order")
    private Set<DiningTable> diningTableSet;
    @JsonIgnore
    @OneToMany(mappedBy = "order")
    private Set<OrderDetail> orderDetailSet;

    public Order() {
    }

    public boolean isEmployeeOrder() {
        return isEmployeeOrder;
    }

    public void setEmployeeOrder(boolean employeeOrder) {
        isEmployeeOrder = employeeOrder;
    }

    public int isStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Timestamp orderTime) {
        this.orderTime = orderTime;
    }

    public Timestamp getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(Timestamp reservationTime) {
        this.reservationTime = reservationTime;
    }

    public Timestamp getActualDelivery() {
        return actualDelivery;
    }

    public void setActualDelivery(Timestamp actualDelivery) {
        this.actualDelivery = actualDelivery;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Set<DiningTable> getDiningTableSet() {
        return diningTableSet;
    }

    public void setDiningTableSet(Set<DiningTable> diningTableSet) {
        this.diningTableSet = diningTableSet;
    }

    public Set<OrderDetail> getOrderDetailSet() {
        return orderDetailSet;
    }

    public void setOrderDetailSet(Set<OrderDetail> orderDetailSet) {
        this.orderDetailSet = orderDetailSet;
    }
}
