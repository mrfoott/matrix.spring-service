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
public class Order {

    private UUID uuid;
    private Integer version;
    private BigDecimal total_price;
    private BigDecimal shipping_fee;
    private String payment_method;
    private String payment_status;
    private UUID user_id;
    private UUID receiver_info_id;

}
