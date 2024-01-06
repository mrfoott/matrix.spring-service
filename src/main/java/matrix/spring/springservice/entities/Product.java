package matrix.spring.springservice.entities;

import jakarta.persistence.Entity;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private UUID id;
    private Integer version;
    private String product_name;
    private String product_description;
    private BigDecimal price;
    private Integer product_quantity;
    private String brand;
    private Integer sold_quantity;
    private LocalDateTime is_deleted;

}
