package matrix.spring.springservice.mappers;

import matrix.spring.springservice.entities.ViewHistory;
import matrix.spring.springservice.models.ViewHistoryDTO;
import org.mapstruct.Mapper;

@Mapper
public interface ViewHistoryMapper {

    ViewHistory viewHistoryDtoToViewHistory(ViewHistoryDTO viewHistoryDTO);

    ViewHistoryDTO viewHistoryToViewHistoryDto(ViewHistory viewHistory);

}
