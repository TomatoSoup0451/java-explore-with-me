package ru.practicum.ewm.main.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.main.service.model.Category;

public interface CategoriesRepository extends JpaRepository<Category, Long> {

}
