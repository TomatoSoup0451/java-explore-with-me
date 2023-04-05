package ru.practicum.ewm.main.service.service.pub;

import org.springframework.data.domain.Pageable;
import ru.practicum.ewm.main.service.dto.event.EventFullDto;
import ru.practicum.ewm.main.service.dto.event.EventShortDto;

import java.util.List;

public interface PublicEventsService {
    List<EventShortDto> getEventsByParams(String text,
                                          List<Long> categories,
                                          Boolean paid,
                                          String rangeStart,
                                          String rangeEnd,
                                          Boolean onlyAvailable,
                                          String sort,
                                          Pageable pageable);

    EventFullDto getEventById(long id);
}
