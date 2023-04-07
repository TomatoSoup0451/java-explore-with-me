package ru.practicum.ewm.main.service.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main.service.dto.category.CategoryDto;
import ru.practicum.ewm.main.service.dto.category.NewCategoryDto;
import ru.practicum.ewm.main.service.service.admin.AdminCategoriesService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/categories")
public class AdminCategoriesController {
    private final AdminCategoriesService categoriesService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CategoryDto addNewCategory(@Valid @RequestBody NewCategoryDto newCategoryDto) {
        return categoriesService.addNewCategory(newCategoryDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/{catId}")
    public void deleteCategory(@PathVariable long catId) {
        categoriesService.deleteCategory(catId);
    }

    @PatchMapping(path = "/{catId}")
    public CategoryDto updateCategory(@PathVariable long catId, @Valid @RequestBody NewCategoryDto newCategoryDto) {
        return categoriesService.updateCategory(catId, newCategoryDto);
    }

}
