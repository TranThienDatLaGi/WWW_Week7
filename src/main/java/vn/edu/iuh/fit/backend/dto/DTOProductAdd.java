package vn.edu.iuh.fit.backend.dto;

import vn.edu.iuh.fit.backend.models.Product;
import vn.edu.iuh.fit.backend.models.ProductImage;
import vn.edu.iuh.fit.backend.models.ProductPrice;

public class DTOProductAdd {
    private Product product;
    private ProductPrice productPrice;
    private ProductImage productImage;

    public DTOProductAdd(Product product, ProductPrice productPrice, ProductImage productImage) {
        this.product = product;
        this.productPrice = productPrice;
        this.productImage = productImage;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductPrice getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(ProductPrice productPrice) {
        this.productPrice = productPrice;
    }

    public ProductImage getProductImage() {
        return productImage;
    }

    public void setProductImage(ProductImage productImage) {
        this.productImage = productImage;
    }
}
