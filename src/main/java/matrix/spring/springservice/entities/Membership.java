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
public class Membership {

    private UUID id;
    private Integer version;
    private Integer membership_rank;
    private Integer discount_percentage;
    private BigDecimal min_price;
    private BigDecimal max_price;
    private LocalDateTime is_deleted;

}
