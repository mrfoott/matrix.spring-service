package matrix.spring.springservice.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "receiver_info")
public class ReceiverInfo {

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
    @Column(name = "receiver_name")
    private String receiverName;

    @NotNull
    @NotBlank
    @Column(name = "receiver_address")
    private String receiverAddress;

    @NotNull
    @NotBlank
    @Column(name = "receiver_phone")
    private String receiverPhone;

    @NotNull
    @Column(name = "is_default")
    private Integer isDefault;

    @Column(name = "is_deleted")
    private LocalDateTime isDeleted;

//    @Column(name = "user_id")
//    @JdbcTypeCode(SqlTypes.CHAR)
//    private UUID userId;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}
