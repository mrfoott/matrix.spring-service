package matrix.spring.springservice.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "membership")
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Version
    private Integer version;

    @Column(name = "membership_rank")
    private String membershipRank;

    @Column(name = "discount_percentage")
    private Integer discountPercentage;

    @Column(name = "min_price")
    private BigDecimal minPrice;

    @Column(name = "max_price")
    private BigDecimal maxPrice;

    @Column(name = "is_deleted")
    private LocalDateTime isDeleted;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "membership")
    private List<User> userList;

}
