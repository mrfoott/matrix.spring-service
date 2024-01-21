package matrix.spring.springservice.models;

import jakarta.persistence.Version;
import lombok.Builder;
import lombok.Data;
import matrix.spring.springservice.entities.ViewHistory;

import java.util.UUID;

@Builder
@Data
public class ViewHistoryDTO {

    private UUID id;
    private Integer version;
    private Integer view;
    private UUID user_id;
    private UUID product_id;

}
