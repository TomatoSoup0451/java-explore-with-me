package ru.practicum.ewm.main.service.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.practicum.ewm.main.service.exception.BadRequestException;

public class Globals {
    public static Pageable getPageable(int from, int size) {
        if (from < 0) {
            throw new BadRequestException("Pagination parameter from should not be negative but was " + from);
        } else if (size <= 0) {
            throw new BadRequestException("Pagination parameter size should be positive but was " + size);
        }
        return PageRequest.of(from / size, size, Sort.by("id").ascending());
    }
}
