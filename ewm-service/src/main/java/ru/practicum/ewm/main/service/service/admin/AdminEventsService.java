package ru.practicum.ewm.main.service.service.admin;

import org.springframework.data.domain.Pageable;
import ru.practicum.ewm.main.service.dto.event.EventFullDto;
import ru.practicum.ewm.main.service.dto.event.UpdateEventAdminRequest;
import ru.practicum.ewm.main.service.model.Event;

import java.util.List;

public interface AdminEventsService {
    List<EventFullDto> getEventsByParameters(List<Long> users,
                                             List<String> states,
                                             List<Long> categories,
                                             String rangeStart,
                                             String rangeEnd,
                                             Pageable pageable);

    Event updateEvent(long eventId, UpdateEventAdminRequest eventDto);
}
