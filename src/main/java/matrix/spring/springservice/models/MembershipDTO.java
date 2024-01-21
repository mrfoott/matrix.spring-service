package matrix.spring.springservice.models;

import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class MembershipDTO {

    private UUID id;
    private Integer version;

    @NotNull
    private Integer membership_rank;

    @NotNull
    private Integer discount_percentage;

    @NotNull
    @Positive
    private BigDecimal min_price;

    @NotNull
    @Positive
    private BigDecimal max_price;

    private LocalDateTime is_deleted;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

}
