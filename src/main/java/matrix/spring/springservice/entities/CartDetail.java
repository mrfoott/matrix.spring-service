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
public class CartDetail {

    private UUID id;
    private Integer version;
    private UUID user_id;
    private Integer item_quantity;

}
