package matrix.spring.springservice.mappers;

import matrix.spring.springservice.entities.Shipping;
import matrix.spring.springservice.models.ShippingDTO;
import org.mapstruct.Mapper;

@Mapper
public interface ShippingMapper {

    Shipping shippingDtoToShipping(ShippingDTO shippingDTO);

    ShippingDTO shippingToShippingDto(Shipping shipping);

}
