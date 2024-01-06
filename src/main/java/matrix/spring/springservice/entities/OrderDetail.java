package matrix.spring.springservice.entities;

import jakarta.persistence.Entity;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {

    private UUID id;
    private Integer version;
    private Integer order_quantity;
    private BigDecimal price_at_order;
    private String product_name_at_order;
    private UUID order_id;
    private UUID product_id;

}
