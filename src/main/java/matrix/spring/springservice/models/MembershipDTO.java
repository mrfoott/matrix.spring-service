package matrix.spring.springservice.models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
public class MembershipDTO {

    private Integer id;
    private Integer version;

    @NotNull
    private Integer membershipRank;

    @NotNull
    private Integer discountPercentage;

    @NotNull
    @Positive
    private BigDecimal minPrice;

    @NotNull
    @Positive
    private BigDecimal maxPrice;

    private LocalDateTime isDeleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
