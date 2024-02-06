package matrix.spring.springservice.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column(length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
    private UUID id;

    @Version
    private Integer version;

    @NotNull
    @NotBlank
    private String userEmail;

    @NotNull
    @NotBlank
    private String password;

    @NotNull
    @NotBlank
    private String fullName;

    @NotNull
    @NotBlank
    private String userPhone;

    @NotNull
    @NotBlank
    private String avatar;

    @NotNull
    private Double membershipPoint;

    private LocalDateTime membershipPromotedDay;
    private LocalDateTime membershipExpiredDay;
    private LocalDateTime isDeleted;
    private Integer roleId;
    private Integer membershipId;

    @CreationTimestamp
    private LocalDateTime created_at;

    @UpdateTimestamp
    private LocalDateTime updated_at;

    @ManyToOne
    private Membership membership;

}
