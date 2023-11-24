package vn.edu.iuh.fit.backend.pks;

import lombok.*;
import vn.edu.iuh.fit.backend.models.Product;

import java.io.Serializable;
import java.time.LocalDateTime;

@Setter @Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ProductPricePK implements Serializable {
    private long product;
    private LocalDateTime price_date_time;
}
