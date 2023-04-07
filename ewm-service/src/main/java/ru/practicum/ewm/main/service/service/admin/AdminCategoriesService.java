package ru.practicum.ewm.main.service.service.admin;

import ru.practicum.ewm.main.service.dto.category.CategoryDto;
import ru.practicum.ewm.main.service.dto.category.NewCategoryDto;

public interface AdminCategoriesService {
    CategoryDto addNewCategory(NewCategoryDto newCategoryDto);

    void deleteCategory(long catId);

    CategoryDto updateCategory(long catId, NewCategoryDto newCategoryDto);
}
