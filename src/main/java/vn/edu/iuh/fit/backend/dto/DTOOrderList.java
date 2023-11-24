package vn.edu.iuh.fit.backend.dto;

import vn.edu.iuh.fit.backend.models.Order;

public class DTOOrderList {
    private Order order;
    private double totalPrice;

    public DTOOrderList(Order order, double totalPrice) {
        this.order = order;
        this.totalPrice = totalPrice;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
