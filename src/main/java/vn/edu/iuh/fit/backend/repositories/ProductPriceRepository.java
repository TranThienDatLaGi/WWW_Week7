package vn.edu.iuh.fit.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.edu.iuh.fit.backend.models.ProductPrice;
import vn.edu.iuh.fit.backend.pks.ProductPricePK;

import java.util.List;
import java.util.Optional;

public interface ProductPriceRepository extends JpaRepository<ProductPrice, ProductPricePK> {
   @Query("select pp from ProductPrice pp where pp.product.product_id = :id order by pp.price_date_time desc limit 1")

    public Optional<ProductPrice> findProductPriceNewestByProductID(@Param("id") long id);
    @Query("select pp from ProductPrice pp where pp.product.product_id = :productID")
    public List<ProductPrice> findProductPricesByProduct_Product_id(@Param("productID") long productID);

}