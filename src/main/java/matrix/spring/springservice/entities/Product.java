package matrix.spring.springservice.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

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
public class Product {

    @Id
    @GeneratedValue
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
    private UUID id;

    @Version
    private Integer version;

    @NotNull
    @NotBlank
    private String productName;

    @NotNull
    @NotBlank
    private String productDescription;

    @NotNull
    @Positive
    private BigDecimal price;

    @NotNull
    @PositiveOrZero
    private Integer productQuantity;

    @NotNull
    @NotBlank
    private String brand;

    @NotNull
    @PositiveOrZero
    private Integer soldQuantity;

    private LocalDateTime isDeleted;

    private Integer categoryId;

    @CreationTimestamp
    private LocalDateTime created_at;

    @UpdateTimestamp
    private LocalDateTime updated_at;

    @OneToMany(mappedBy = "product")
    private List<ProductImage> productImages;

}
