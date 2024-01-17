package matrix.spring.springservice.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    private String product_name;

    @NotNull
    @NotBlank
    private String product_description;

    @NotNull
    @Positive
    private BigDecimal price;

    @NotNull
    @Positive
    private Integer product_quantity;

    @NotNull
    @NotBlank
    private String brand;

    @NotNull
    @Positive
    private Integer sold_quantity;

    private LocalDateTime is_deleted;

}
