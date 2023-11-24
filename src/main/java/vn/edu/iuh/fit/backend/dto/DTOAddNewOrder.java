package vn.edu.iuh.fit.backend.dto;

import vn.edu.iuh.fit.backend.models.Order;
import vn.edu.iuh.fit.backend.models.OrderDetail;

public class DTOAddNewOrder {
    private Order order;
    private OrderDetail orderDetail;

    public DTOAddNewOrder(Order order, OrderDetail orderDetail) {
        this.order = order;
        this.orderDetail = orderDetail;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public OrderDetail getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(OrderDetail orderDetail) {
        this.orderDetail = orderDetail;
    }
}
