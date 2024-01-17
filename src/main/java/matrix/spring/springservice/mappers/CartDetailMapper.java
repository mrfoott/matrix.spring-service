package matrix.spring.springservice.mappers;

import matrix.spring.springservice.entities.CartDetail;
import matrix.spring.springservice.models.CartDetailDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CartDetailMapper {

    CartDetail cartDetailDtoToCartDetail(CartDetailDTO cartDetailDTO);

    CartDetailDTO cartDetailToCartDetailDto(CartDetail cartDetail);

}
