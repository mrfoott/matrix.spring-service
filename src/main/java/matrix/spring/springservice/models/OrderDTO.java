package matrix.spring.springservice.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Data
public class OrderDTO {

    private UUID id;
    private Integer version;

    private BigDecimal totalPrice;

    private Integer discountPercentage;

    private BigDecimal shippingFee;

    private String paymentMethod;

    private String paymentStatus;

    private UUID userId;
    private UUID receiverInfoId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<String> cartDetailIdList;

}
