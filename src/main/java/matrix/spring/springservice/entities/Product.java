package matrix.spring.springservice.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
    private UUID id;

    @Version
    private Integer version;

    @NotNull
    @NotBlank
    @Column(name = "product_name")
    private String productName;

    @NotNull
    @NotBlank
    @Column(name = "product_description")
    private String productDescription;

    @NotNull
    @Positive
    private BigDecimal price;

    @NotNull
    @PositiveOrZero
    @Column(name = "product_quantity")
    private Integer productQuantity;

    @NotNull
    @NotBlank
    private String brand;

    @NotNull
    @PositiveOrZero
    @Column(name = "sold_quantity")
    private Integer soldQuantity;

    @Column(name = "is_deleted")
    private LocalDateTime isDeleted;

//    private Integer categoryId;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "product")
    private List<ProductImage> productImages;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
