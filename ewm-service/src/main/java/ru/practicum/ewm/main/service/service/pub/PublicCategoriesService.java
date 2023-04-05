package ru.practicum.ewm.main.service.service.pub;

import org.springframework.data.domain.Pageable;
import ru.practicum.ewm.main.service.dto.category.CategoryDto;

import java.util.List;

public interface PublicCategoriesService {
    CategoryDto getCategoryById(long catId);

    List<CategoryDto> getAllCategories(Pageable pageable);

}
