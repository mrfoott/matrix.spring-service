package matrix.spring.springservice.mappers;

import matrix.spring.springservice.entities.ReceiverInfo;
import matrix.spring.springservice.models.ReceiverInfoDTO;
import org.mapstruct.Mapper;

@Mapper
public interface ReceiverInfoMapper {

    ReceiverInfo receiverInfoDtoToReceiverInfo(ReceiverInfoDTO receiverInfoDTO);

    ReceiverInfoDTO receiverInfoToReceiverInfoDto(ReceiverInfo receiverInfo);

}
