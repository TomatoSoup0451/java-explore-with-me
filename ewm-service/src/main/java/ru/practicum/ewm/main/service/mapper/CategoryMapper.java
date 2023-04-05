package ru.practicum.ewm.main.service.mapper;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import ru.practicum.ewm.main.service.dto.category.CategoryDto;
import ru.practicum.ewm.main.service.dto.category.NewCategoryDto;
import ru.practicum.ewm.main.service.model.Category;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toCategoryDto(Category category);

    List<CategoryDto> toCategoryDtos(List<Category> category);

    Category newCategoryDtoToCategory(NewCategoryDto newCategoryDto);

    List<CategoryDto> toCategoryDtos(Page<Category> pageable);
}
