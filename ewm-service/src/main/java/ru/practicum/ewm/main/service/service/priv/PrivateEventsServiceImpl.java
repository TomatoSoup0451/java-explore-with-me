package ru.practicum.ewm.main.service.service.priv;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.main.service.dto.event.EventFullDto;
import ru.practicum.ewm.main.service.dto.event.EventShortDto;
import ru.practicum.ewm.main.service.dto.event.NewEventDto;
import ru.practicum.ewm.main.service.dto.event.UpdateEventUserRequest;
import ru.practicum.ewm.main.service.dto.participationrequest.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.main.service.dto.participationrequest.EventRequestStatusUpdateResult;
import ru.practicum.ewm.main.service.dto.participationrequest.ParticipationRequestDto;
import ru.practicum.ewm.main.service.exception.BadRequestException;
import ru.practicum.ewm.main.service.exception.EntityNotFoundException;
import ru.practicum.ewm.main.service.mapper.EventMapper;
import ru.practicum.ewm.main.service.mapper.ParticipationRequestMapper;
import ru.practicum.ewm.main.service.model.Event;
import ru.practicum.ewm.main.service.model.ParticipationRequest;
import ru.practicum.ewm.main.service.model.enums.EventState;
import ru.practicum.ewm.main.service.model.enums.ParticipationStatus;
import ru.practicum.ewm.main.service.model.enums.StateActionUser;
import ru.practicum.ewm.main.service.repository.*;
import ru.practicum.ewm.stat.client.StatsClient;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrivateEventsServiceImpl implements PrivateEventsService {

    private final EventsRepository eventsRepository;
    private final UsersRepository usersRepository;
    private final ParticipationRequestsRepository participationRequestsRepository;
    private final ReactionsRepository reactionsRepository;
    private final CategoriesRepository categoriesRepository;

    private final EventMapper eventMapper;
    private final ParticipationRequestMapper requestMapper;
    private final StatsClient statsClient;

    @Override
    public List<EventShortDto> getEventsByUserId(long userId, Pageable pageable) {
        return eventMapper.toEventShortDtos(eventsRepository.findAllByInitiatorId(userId, pageable),
                participationRequestsRepository, reactionsRepository, statsClient);
    }

    @Override
    public EventFullDto addNewEvent(long userId, NewEventDto newEventDto) {
        Event event = eventMapper.toEvent(newEventDto, categoriesRepository);
        checkEventStart(event);
        event.setInitiator(usersRepository.findById(userId)
                .orElseThrow((() -> new EntityNotFoundException("User with id = " + userId + " not found"))));
        event.setState(EventState.PENDING);
        event.setCreatedOn(Date.from(Instant.now()));
        Event result = eventsRepository.save(event);
        log.info("Event with id = {} added", result.getId());
        return eventMapper.toEventFullDto(result,
                participationRequestsRepository,
                reactionsRepository,
                statsClient);
    }

    @Override
    public EventFullDto getEventFullInfo(long userId, long eventId) {
        return eventMapper.toEventFullDto(eventsRepository.findByIdAndInitiatorId(eventId, userId)
                        .orElseThrow((() -> new EntityNotFoundException("Event with id = " + eventId + " not found"))),
                participationRequestsRepository,
                reactionsRepository,
                statsClient);
    }

    @Override
    public EventFullDto updateEvent(long userId, long eventId, UpdateEventUserRequest updatedEventDto) {
        Event oldEvent = eventsRepository.findByIdAndInitiatorId(eventId, userId)
                .orElseThrow((() -> new EntityNotFoundException("Event with id = " + eventId + " not found")));
        checkEventStart(oldEvent);
        if (oldEvent.getState() == EventState.PUBLISHED) {
            throw new DataIntegrityViolationException("Only pending or canceled events can be changed");
        }
        if (updatedEventDto.getStateAction() != null) {
            StateActionUser actionUser = StateActionUser.valueOf(updatedEventDto.getStateAction());
            oldEvent.setState(actionUser == StateActionUser.CANCEL_REVIEW ? EventState.CANCELED : EventState.PENDING);
        }
        eventMapper.updateEventFromUpdateEventUserRequest(updatedEventDto, oldEvent, categoriesRepository);
        checkEventStart(oldEvent);
        Event result = eventsRepository.save(oldEvent);
        log.info("Event with id = {} updated", result.getId());
        return eventMapper.toEventFullDto(result,
                participationRequestsRepository,
                reactionsRepository,
                statsClient);
    }

    @Override
    public List<ParticipationRequestDto> getEventRequests(long userId, long eventId) {
        Event event = eventsRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event with id = " + eventId + "not found"));
        if (userId != event.getInitiator().getId()) {
            throw new BadRequestException("User with id = " + userId + "doesn't have access to requests for " +
                    "event with id = " + eventId);
        }
        return requestMapper.toParticipationRequestsDtos(participationRequestsRepository.findAllByEventId(eventId));
    }

    @Override
    public EventRequestStatusUpdateResult updateEventRequests(long userId,
                                                              long eventId,
                                                              EventRequestStatusUpdateRequest requestUpdate) {

        Event event = eventsRepository.findByIdAndInitiatorId(eventId, userId)
                .orElseThrow((() -> new EntityNotFoundException("Event with id = " + eventId + " not found")));
        checkEventStart(event);

        Map<Long, ParticipationRequest> currentRequests = participationRequestsRepository
                .findAllByEventId(eventId).stream()
                .collect(Collectors.toMap(ParticipationRequest::getId, r -> r));

        long approvedParticipants = currentRequests.values()
                .stream()
                .filter(r -> r.getStatus() == ParticipationStatus.CONFIRMED)
                .count();

        List<ParticipationRequest> updatedRequests = new ArrayList<>();
        ParticipationStatus status = ParticipationStatus.valueOf(requestUpdate.getStatus());

        if (status == ParticipationStatus.CONFIRMED && approvedParticipants >= event.getParticipantLimit()
                && event.getParticipantLimit() != 0) {
            throw new DataIntegrityViolationException("Event exceed participants limit");
        }

        for (long requestId : requestUpdate.getRequestIds()) {
            ParticipationRequest r = Optional.ofNullable(currentRequests.get(requestId))
                    .orElseThrow(() -> new EntityNotFoundException("Request with id = " + requestId + " not found"));
            if (r.getStatus() == ParticipationStatus.CONFIRMED && status == ParticipationStatus.REJECTED) {
                throw new DataIntegrityViolationException("Can not decline already approved request");
            }
            if (approvedParticipants >= event.getParticipantLimit() && event.getParticipantLimit() != 0) {
                r.setStatus(ParticipationStatus.REJECTED);
            } else {
                r.setStatus(status);
            }
            updatedRequests.add(r);
        }
        updatedRequests = participationRequestsRepository.saveAll(updatedRequests);
        return EventRequestStatusUpdateResult.builder()
                .confirmedRequests(requestMapper.toParticipationRequestsDtos(updatedRequests.stream()
                        .filter(r -> r.getStatus() == ParticipationStatus.CONFIRMED)
                        .collect(Collectors.toList())))
                .rejectedRequests(requestMapper.toParticipationRequestsDtos(updatedRequests.stream()
                        .filter(r -> r.getStatus() == ParticipationStatus.REJECTED)
                        .collect(Collectors.toList())))
                .build();
    }

    private void checkEventStart(Event event) {
        if (event.getEventDate().toInstant().minus(Duration.ofHours(2)).isBefore(Instant.now())) {
            throw new DataIntegrityViolationException("Event time should be at least 2 hours later from current time");
        }
    }
}
