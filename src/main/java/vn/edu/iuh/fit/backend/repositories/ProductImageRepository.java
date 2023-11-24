package vn.edu.iuh.fit.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.edu.iuh.fit.backend.models.ProductImage;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    @Query("select PI from ProductImage PI where PI.product.product_id = :productID")
    public List<ProductImage> findProductImagesByProductID(@Param("productID") long productID);
}