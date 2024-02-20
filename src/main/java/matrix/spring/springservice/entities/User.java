package matrix.spring.springservice.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
    private UUID id;

    @Version
    private Integer version;

    @NotNull
    @NotBlank
    @Column(name = "user_email")
    private String userEmail;

    @NotNull
    @NotBlank
    private String password;

    @NotNull
    @NotBlank
    @Column(name = "full_name")
    private String fullName;

    @NotNull
    @NotBlank
    @Column(name = "user_phone")
    private String userPhone;

    @NotNull
    @NotBlank
    private String avatar;

    @NotNull
    @Column(name = "membership_point")
    private Double membershipPoint;

    @Column(name = "membership_promoted_day")
    private LocalDateTime membershipPromotedDay;

    @Column(name = "membership_expired_day")
    private LocalDateTime membershipExpiredDay;

    @Column(name = "is_deleted")
    private LocalDateTime isDeleted;

//    @Column(name = "role_id")
//    private Integer roleId;

//    @Column(name = "membership_id")
//    private Integer membershipId;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "membership_id")
    private Membership membership;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

}
