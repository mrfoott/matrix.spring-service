package matrix.spring.springservice.entities;

import jakarta.persistence.Entity;
import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Shipping {

    private UUID id;
    private Integer version;
    private String shipping_status;
    private String shipping_location;
    private UUID order_id;

}
