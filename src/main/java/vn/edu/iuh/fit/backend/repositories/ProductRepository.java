package vn.edu.iuh.fit.backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.iuh.fit.backend.enums.ProductStatus;
import vn.edu.iuh.fit.backend.models.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    public Page<Product> findAllByStatusNot(ProductStatus productStatus, Pageable pageable);
}