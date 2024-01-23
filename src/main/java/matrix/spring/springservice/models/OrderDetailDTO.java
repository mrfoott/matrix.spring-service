package matrix.spring.springservice.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class OrderDetailDTO {

    private UUID id;
    private Integer version;

    @NotNull
    @Positive
    private Integer orderQuantity;

    @NotNull
    @Positive
    private BigDecimal priceAtOrder;

    @NotNull
    @NotBlank
    private String productNameAtOrder;

    private UUID orderId;
    private UUID productId;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

}
