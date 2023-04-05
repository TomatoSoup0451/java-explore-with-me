package ru.practicum.ewm.main.service.service.admin;

import ru.practicum.ewm.main.service.dto.category.NewCategoryDto;
import ru.practicum.ewm.main.service.model.Category;

public interface AdminCategoriesService {
    Category addNewCategory(NewCategoryDto newCategoryDto);

    void deleteCategory(long catId);

    Category updateCategory(long catId, NewCategoryDto newCategoryDto);
}
