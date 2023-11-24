package vn.edu.iuh.fit.backend.dto;

import vn.edu.iuh.fit.backend.models.Product;

public class DTOCartList {
    private Product product;
    private double totalPrice;
    private int quantity;

    public DTOCartList(Product product, double totalPrice, int quantity) {
        this.product = product;
        this.totalPrice = totalPrice;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

