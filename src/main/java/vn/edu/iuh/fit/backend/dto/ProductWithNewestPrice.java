package vn.edu.iuh.fit.backend.dto;

import vn.edu.iuh.fit.backend.models.Product;
import vn.edu.iuh.fit.backend.models.ProductPrice;

public class ProductWithNewestPrice {
    private Product product;
    private ProductPrice productPrice;

    public ProductWithNewestPrice(Product product, ProductPrice productPrice) {
        this.product = product;
        this.productPrice = productPrice;
    }

    public Product getProduct() {
        return product;
    }

    public ProductPrice getProductPrice() {
        return productPrice;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setProductPrice(ProductPrice productPrice) {
        this.productPrice = productPrice;
    }
}
