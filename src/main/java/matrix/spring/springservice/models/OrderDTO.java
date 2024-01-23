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
public class OrderDTO {

    private UUID id;
    private Integer version;

    @NotNull
    @Positive
    private BigDecimal totalPrice;

    @NotNull
    @Positive
    private BigDecimal shippingFee;

    @NotNull
    @NotBlank
    private String paymentMethod;

    @NotNull
    @NotBlank
    private String paymentStatus;

    private UUID userId;
    private UUID receiverInfoId;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

}
