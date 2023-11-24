package vn.edu.iuh.fit.backend.repositories;

import java.time.LocalDateTime;

public interface OrderCount {
    LocalDateTime getOrderDate();
    Integer getTotalOrderNumber();
}
