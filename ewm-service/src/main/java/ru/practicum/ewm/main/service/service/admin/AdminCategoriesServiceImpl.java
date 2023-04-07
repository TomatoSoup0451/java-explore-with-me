package ru.practicum.ewm.main.service.service.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.main.service.dto.category.CategoryDto;
import ru.practicum.ewm.main.service.dto.category.NewCategoryDto;
import ru.practicum.ewm.main.service.exception.EntityNotFoundException;
import ru.practicum.ewm.main.service.mapper.CategoryMapper;
import ru.practicum.ewm.main.service.model.Category;
import ru.practicum.ewm.main.service.repository.CategoriesRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminCategoriesServiceImpl implements AdminCategoriesService {

    private final CategoriesRepository categoriesRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDto addNewCategory(NewCategoryDto newCategoryDto) {
        Category result = categoriesRepository.save(categoryMapper.newCategoryDtoToCategory(newCategoryDto));
        log.info("Category with id = {} added", result.getId());
        return categoryMapper.toCategoryDto(result);
    }

    @Override
    public void deleteCategory(long catId) {
        categoriesRepository.deleteById(catId);
        log.info("Category with id = {} deleted", catId);
    }

    @Override
    public CategoryDto updateCategory(long catId, NewCategoryDto newCategoryDto) {
        Category oldCategory = categoriesRepository.findById(catId)
                .orElseThrow((() -> new EntityNotFoundException("Category with id = " + catId + " not found")));
        oldCategory.setName(newCategoryDto.getName());
        log.info("Category with id = {} updated", oldCategory.getId());
        return categoryMapper.toCategoryDto(categoriesRepository.save(oldCategory));
    }
}
