package ru.practicum.ewm.main.service.service.priv;

import org.springframework.data.domain.Pageable;
import ru.practicum.ewm.main.service.dto.event.EventFullDto;
import ru.practicum.ewm.main.service.dto.event.EventShortDto;
import ru.practicum.ewm.main.service.dto.event.NewEventDto;
import ru.practicum.ewm.main.service.dto.event.UpdateEventUserRequest;
import ru.practicum.ewm.main.service.dto.participationrequest.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.main.service.dto.participationrequest.EventRequestStatusUpdateResult;
import ru.practicum.ewm.main.service.dto.participationrequest.ParticipationRequestDto;
import ru.practicum.ewm.main.service.model.Event;

import java.util.List;

public interface PrivateEventsService {
    List<EventShortDto> getEventsByUserId(long userId, Pageable pageable);

    Event addNewEvent(long userId, NewEventDto newEventDto);

    EventFullDto getEventFullInfo(long userId, long eventId);

    Event updateEvent(long userId, long eventId, UpdateEventUserRequest updatedEventDto);

    List<ParticipationRequestDto> getEventRequests(long userId, long eventId);

    EventRequestStatusUpdateResult updateEventRequests(long userId,
                                                       long eventId,
                                                       EventRequestStatusUpdateRequest request);
}
