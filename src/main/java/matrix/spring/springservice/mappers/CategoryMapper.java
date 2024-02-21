package matrix.spring.springservice.mappers;

import matrix.spring.springservice.entities.Category;
import matrix.spring.springservice.models.CategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { EntityMapper.class })
public interface CategoryMapper {

    Category categoryIdToCategory(Integer id);

    Category categoryDtoToCategory(CategoryDTO categoryDTO);

    CategoryDTO categoryToCategoryDto(Category category);

}
