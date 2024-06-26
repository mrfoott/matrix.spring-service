package matrix.spring.springservice.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
    private UUID id;

    @Version
    private Integer version;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "discount_percentage")
    private Integer discountPercentage;

    @Column(name = "shipping_fee")
    private BigDecimal shippingFee;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "payment_status")
    private String paymentStatus;

    @Column(name = "estimated_delivery_date")
    private String estimatedDeliveryDate;

//    @Column(name = "delivery_date")
//    private LocalDate deliveryDate;

    @Column(name = "delivery_date")
    private LocalDate deliveryDate;

    @Column(name = "bill_of_lading_code")
    private String billOfLadingCode;

    @Column(name = "shipping_unit")
    private String shippingUnit;

//    @Column(name = "receiver_info_id")
//    private UUID receiverInfoId;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "receiver_info_id")
    private ReceiverInfo receiverInfo;

    @OneToOne(mappedBy = "order")
    private Shipping shipping;

}
