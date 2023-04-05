package ru.practicum.ewm.main.service.service.pub;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.main.service.dto.event.EventFullDto;
import ru.practicum.ewm.main.service.dto.event.EventShortDto;
import ru.practicum.ewm.main.service.exception.IdNotFoundException;
import ru.practicum.ewm.main.service.mapper.EventMapper;
import ru.practicum.ewm.main.service.model.enums.EventState;
import ru.practicum.ewm.main.service.model.enums.Sort;
import ru.practicum.ewm.main.service.repository.CategoriesRepository;
import ru.practicum.ewm.main.service.repository.EventsRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublicEventsServiceImpl implements PublicEventsService {
    private final EventsRepository eventsRepository;
    private final String datePattern = "yyyy-MM-dd HH:mm:ss";
    private final SimpleDateFormat formatter = new SimpleDateFormat(datePattern);
    private final EventMapper eventMapper;
    private final CategoriesRepository categoriesRepository;

    @Override
    public List<EventShortDto> getEventsByParams(String text, List<Long> categories, Boolean paid, String rangeStart,
                                                 String rangeEnd, Boolean onlyAvailable,
                                                 String sort, Pageable pageable) {
        Date startDate = null;
        Date endDate = null;
        try {
            if (rangeStart != null) {
                startDate = formatter.parse(rangeStart);
            }
        } catch (ParseException e) {
            throw new IllegalArgumentException("Illegal start date format. Pattern should be " + datePattern +
                    "but actually was " + rangeStart);
        }
        try {
            if (rangeEnd != null) {
                endDate = formatter.parse(rangeEnd);
            }
        } catch (ParseException e) {
            throw new IllegalArgumentException("Illegal end date format. Pattern should be " + datePattern +
                    "but actually was " + rangeEnd);
        }
        if (startDate == null && endDate == null) {
            startDate = Date.from(Instant.now());
        }

        List<EventShortDto> events = eventMapper.toEventShortDtos(eventsRepository.findAllByParamsPublic(
                text,
                (categories == null) ? null : categoriesRepository.findAllById(categories),
                paid,
                startDate,
                endDate,
                onlyAvailable,
                pageable));
        if (sort != null) {
            switch (Sort.valueOf(sort)) {
                case EVENT_DATE:
                    return events.stream()
                            .sorted(Comparator.comparing(EventShortDto::getEventDate))
                            .collect(Collectors.toList());
                case VIEWS:
                    return events.stream()
                            .sorted(Comparator.comparing(EventShortDto::getViews))
                            .collect(Collectors.toList());
            }
        }
        return events;
    }

    @Override
    public EventFullDto getEventById(long eventId) {
        return eventMapper.toEventFullDto(eventsRepository.findByIdAndState(eventId, EventState.PUBLISHED)
                .orElseThrow(() -> new IdNotFoundException("Event with id = " + eventId + " not found")));
    }
}
