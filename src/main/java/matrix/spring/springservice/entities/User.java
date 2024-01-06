package matrix.spring.springservice.entities;

import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private UUID id;
    private Integer version;
    private String user_email;
    private String password;
    private String full_name;
    private String user_phone;
    private String avatar;
    private Double membership_point;
    private LocalDateTime membership_promoted_day;
    private LocalDateTime membership_expired_day;
    private LocalDateTime is_deleted;
    private UUID role_id;
    private UUID membership_id;

}
