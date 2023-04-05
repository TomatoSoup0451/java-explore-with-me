package ru.practicum.ewm.main.service.service.pub;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.main.service.dto.category.CategoryDto;
import ru.practicum.ewm.main.service.exception.IdNotFoundException;
import ru.practicum.ewm.main.service.mapper.CategoryMapper;
import ru.practicum.ewm.main.service.repository.CategoriesRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicCategoriesServiceImpl implements PublicCategoriesService {
    private final CategoriesRepository categoriesRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDto getCategoryById(long catId) {
        return categoryMapper.toCategoryDto(categoriesRepository.findById(catId)
                .orElseThrow(() -> new IdNotFoundException("Category with id = " + catId + " not found")));
    }

    @Override
    public List<CategoryDto> getAllCategories(Pageable pageable) {
        return categoryMapper.toCategoryDtos(categoriesRepository.findAll(pageable));
    }
}
