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

//    public Product(UUID id, Integer version, String productName, String productDescription, BigDecimal price, Integer productQuantity, String brand, Integer soldQuantity, LocalDateTime isDeleted, Category category, LocalDateTime createdAt, LocalDateTime updatedAt, List<ProductImage> productImages) {
//        this.id = id;
//        this.version = version;
//        this.productName = productName;
//        this.productDescription = productDescription;
//        this.price = price;
//        this.productQuantity = productQuantity;
//        this.brand = brand;
//        this.soldQuantity = soldQuantity;
//        this.isDeleted = isDeleted;
//        this.setCategory(category);
//        this.createdAt = createdAt;
//        this.updatedAt = updatedAt;
//        this.productImages = productImages;
//    }

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

    @Column(name = "category_id")
    private Integer categoryId;

//    @ManyToOne
//    @JoinColumn(name = "category_id")
//    private Category category;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

//    @OneToMany(mappedBy = "product")
//    private List<ProductImage> productImages;

//    @OneToMany(mappedBy = "product")
//    private List<Review> productReviews;

}
