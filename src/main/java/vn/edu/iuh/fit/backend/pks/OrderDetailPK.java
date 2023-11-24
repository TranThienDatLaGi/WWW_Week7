package vn.edu.iuh.fit.backend.pks;

import lombok.*;
import vn.edu.iuh.fit.backend.models.Order;
import vn.edu.iuh.fit.backend.models.Product;

import java.io.Serializable;

@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class OrderDetailPK implements Serializable {
    private Order order;
    private Product product;

    public OrderDetailPK(long orderId, long productId) {
    }
}
