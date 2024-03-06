package matrix.spring.springservice.models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
public class MembershipDTO {

    private Integer id;
    private Integer version;

    private Integer membershipRank;

    private Integer discountPercentage;

    private BigDecimal minPrice;

    private BigDecimal maxPrice;

    private LocalDateTime isDeleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<UserDTO> userList;

}
