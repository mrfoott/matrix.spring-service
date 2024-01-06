package matrix.spring.springservice.entities;

import jakarta.persistence.Entity;
import lombok.*;

import java.rmi.server.UID;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ReceiverInfo {

    private UUID id;
    private Integer version;
    private String receiver_name;
    private String receiver_address;
    private String receiver_phone;
    private Integer is_default;
    private LocalDateTime is_deleted;
    private UUID user_id;

}
