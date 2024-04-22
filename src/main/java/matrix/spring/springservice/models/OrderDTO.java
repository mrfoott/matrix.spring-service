package matrix.spring.springservice.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import matrix.spring.springservice.entities.OrderDetail;
import matrix.spring.springservice.entities.Shipping;

import java.math.BigDecimal;
import java.time.LocalDate;
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

    private String estimatedDeliveryDate;

    private LocalDate deliveryDate;

    private String billOfLadingCode;

    private String shippingUnit;

    private UUID userId;
    private UUID receiverInfoId;
    private UUID shippingId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<OrderDetailDTO> orderDetails;

    private List<String> cartDetailIdList;

}
