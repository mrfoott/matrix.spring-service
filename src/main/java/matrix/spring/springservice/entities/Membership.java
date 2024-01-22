package matrix.spring.springservice.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

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

    @Id
    private Integer id;

    @Version
    private Integer version;

    @NotNull
    @PositiveOrZero
    private Integer membership_rank;

    @NotNull
    private Integer discount_percentage;

    @NotNull
    @PositiveOrZero
    private BigDecimal min_price;

    @NotNull
    @Positive
    private BigDecimal max_price;

    private LocalDateTime is_deleted;

    @CreationTimestamp
    private LocalDateTime created_at;

    @UpdateTimestamp
    private LocalDateTime updated_at;

}
