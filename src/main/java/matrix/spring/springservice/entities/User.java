package matrix.spring.springservice.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
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
    private String user_email;

    @NotNull
    @NotBlank
    private String password;

    @NotNull
    @NotBlank
    private String full_name;

    @NotNull
    @NotBlank
    private String user_phone;

    @NotNull
    @NotBlank
    private String avatar;

    @NotNull
    private Double membership_point;

    private LocalDateTime membership_promoted_day;
    private LocalDateTime membership_expired_day;
    private LocalDateTime is_deleted;
    private UUID role_id;
    private UUID membership_id;

    @CreationTimestamp
    private LocalDateTime created_at;

    @UpdateTimestamp
    private LocalDateTime updated_at;

}
