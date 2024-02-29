package matrix.spring.springservice.mappers;

import jakarta.persistence.PersistenceContext;
import matrix.spring.springservice.entities.Category;
import matrix.spring.springservice.entities.Product;
import matrix.spring.springservice.models.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ObjectFactory;
import org.mapstruct.TargetType;
import org.springframework.stereotype.Component;


//@Mapper(componentModel = "spring", uses = { CategoryMapper.class })
@Mapper
public interface ProductMapper {

//    @Mapping(source = "categoryId", target = "category")
    Product productDtoToProduct(ProductDTO productDTO);

    ProductDTO productToProductDto(Product product);

}
