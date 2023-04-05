package ru.practicum.ewm.main.service.controller.pub;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main.service.dto.category.CategoryDto;
import ru.practicum.ewm.main.service.service.pub.PublicCategoriesService;

import java.util.List;

import static ru.practicum.ewm.main.service.util.Globals.getPageable;

@RestController
@RequestMapping(path = "/categories")
@RequiredArgsConstructor
public class PublicCategoriesController {
    private final PublicCategoriesService categoriesService;

    @GetMapping
    public List<CategoryDto> getAllCategories(@RequestParam(defaultValue = "0") int from,
                                              @RequestParam(defaultValue = "10") int size) {
        return categoriesService.getAllCategories(getPageable(from, size));
    }

    @GetMapping(path = "/{catId}")
    public CategoryDto getCategoryById(@PathVariable long catId) {
        return categoriesService.getCategoryById(catId);
    }
}
