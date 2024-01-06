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
public class Category {

    private UUID id;
    private Integer version;
    private String category_name;
    private LocalDateTime is_deleted;

}
