package matrix.spring.springservice.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Membership {

    @Id
    private Integer id;

    @Version
    private Integer version;

    @NotNull
    @PositiveOrZero
    private Integer membershipRank;

    @NotNull
    private Integer discountPercentage;

    @NotNull
    @PositiveOrZero
    private BigDecimal minPrice;

    @NotNull
    @Positive
    private BigDecimal maxPrice;

    private LocalDateTime isDeleted;

    @CreationTimestamp
    private LocalDateTime created_at;

    @UpdateTimestamp
    private LocalDateTime updated_at;

}
