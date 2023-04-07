package ru.practicum.ewm.main.service.service.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.main.service.dto.event.EventFullDto;
import ru.practicum.ewm.main.service.dto.event.UpdateEventAdminRequest;
import ru.practicum.ewm.main.service.exception.EntityNotFoundException;
import ru.practicum.ewm.main.service.mapper.EventMapper;
import ru.practicum.ewm.main.service.model.Event;
import ru.practicum.ewm.main.service.model.enums.EventState;
import ru.practicum.ewm.main.service.model.enums.StateActionAdmin;
import ru.practicum.ewm.main.service.repository.CategoriesRepository;
import ru.practicum.ewm.main.service.repository.EventsRepository;
import ru.practicum.ewm.main.service.repository.UsersRepository;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminEventsServiceImpl implements AdminEventsService {
    private final EventsRepository eventsRepository;
    private final UsersRepository usersRepository;
    private final CategoriesRepository categoriesRepository;
    private final EventMapper eventMapper;
    private final String datePattern = "yyyy-MM-dd HH:mm:ss";
    private final SimpleDateFormat formatter = new SimpleDateFormat(datePattern);

    @Override
    public List<EventFullDto> getEventsByParameters(List<Long> users,
                                                    List<String> states,
                                                    List<Long> categories,
                                                    String rangeStart,
                                                    String rangeEnd,
                                                    Pageable pageable) {
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

        return eventMapper.toEventFullDtos(eventsRepository.findAllByParamsAdmin(
                (users == null) ? null : usersRepository.findAllById(users),
                (states == null) ? null : states.stream().map(EventState::valueOf).collect(Collectors.toList()),
                (categories == null) ? null : categoriesRepository.findAllById(categories),
                startDate,
                endDate,
                pageable));
    }

    @Override
    public EventFullDto updateEvent(long eventId, UpdateEventAdminRequest eventDto) {
        Event oldEvent = eventsRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event with id = " + eventId + " not found"));
        if (oldEvent.getEventDate().toInstant().minus(Duration.ofHours(1)).isBefore(Instant.now())) {
            throw new DataIntegrityViolationException("Event time should be at least 1 hour later from current time");
        }

        if (eventDto.getEventDate() != null &&
                (eventDto.getEventDate()).toInstant().minus(Duration.ofHours(1)).isBefore(Instant.now())) {
            throw new DataIntegrityViolationException("Event time should be at least 1 hour later from current time");
        }

        if (eventDto.getStateAction() != null) {
            StateActionAdmin actionAdmin = StateActionAdmin.valueOf(eventDto.getStateAction());

            if (actionAdmin == StateActionAdmin.PUBLISH_EVENT) {
                if (oldEvent.getState() != EventState.PENDING) {
                    throw new DataIntegrityViolationException("Only pending events can be published");
                } else {
                    oldEvent.setPublishedOn(Timestamp.from(Instant.now()));
                    oldEvent.setState(EventState.PUBLISHED);
                }
            }

            if (actionAdmin == StateActionAdmin.REJECT_EVENT) {
                if (oldEvent.getState() == EventState.PUBLISHED) {
                    throw new DataIntegrityViolationException("Published events can not be rejected");
                } else {
                    oldEvent.setState(EventState.CANCELED);
                }
            }
        }
        eventMapper.updateEventFromUpdateEventAdminRequest(eventDto, oldEvent);
        log.info("Event with id = {} updated", oldEvent.getId());
        return eventMapper.toEventFullDto(eventsRepository.save(oldEvent));
    }
}
