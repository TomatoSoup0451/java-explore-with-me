package ru.practicum.ewm.main.service.controller.priv;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main.service.dto.event.EventFullDto;
import ru.practicum.ewm.main.service.dto.event.EventShortDto;
import ru.practicum.ewm.main.service.dto.event.NewEventDto;
import ru.practicum.ewm.main.service.dto.event.UpdateEventUserRequest;
import ru.practicum.ewm.main.service.dto.participationrequest.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.main.service.dto.participationrequest.EventRequestStatusUpdateResult;
import ru.practicum.ewm.main.service.dto.participationrequest.ParticipationRequestDto;
import ru.practicum.ewm.main.service.service.priv.PrivateEventsService;

import javax.validation.Valid;
import java.util.List;

import static ru.practicum.ewm.main.service.util.Globals.getPageable;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class PrivateEventsController {

    private final PrivateEventsService eventsService;

    @GetMapping(path = "{userId}/events")
    public List<EventShortDto> getEventsByUserId(@PathVariable long userId,
                                                 @RequestParam(required = false, defaultValue = "0") int from,
                                                 @RequestParam(required = false, defaultValue = "10") int to) {
        return eventsService.getEventsByUserId(userId, getPageable(from, to));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "{userId}/events")
    public EventFullDto addNewEvent(@PathVariable long userId, @Valid @RequestBody NewEventDto newEventDto) {
        return eventsService.addNewEvent(userId, newEventDto);
    }

    @GetMapping(path = "{userId}/events/{eventId}")
    public EventFullDto getEventFullInfo(@PathVariable long userId, @PathVariable long eventId) {
        return eventsService.getEventFullInfo(userId, eventId);
    }

    @PatchMapping(path = "{userId}/events/{eventId}")
    public EventFullDto updateEvent(@PathVariable long userId,
                                    @PathVariable long eventId,
                                    @RequestBody @Valid UpdateEventUserRequest updatedEventDto) {
        return eventsService.updateEvent(userId, eventId, updatedEventDto);
    }

    @GetMapping(path = "{userId}/events/{eventId}/requests")
    public List<ParticipationRequestDto> getEventRequests(@PathVariable long userId, @PathVariable long eventId) {
        return eventsService.getEventRequests(userId, eventId);
    }

    @PatchMapping(path = "{userId}/events/{eventId}/requests")
    public EventRequestStatusUpdateResult updateEventRequests(@PathVariable long userId,
                                                              @PathVariable long eventId,
                                                              @RequestBody @Valid EventRequestStatusUpdateRequest request) {
        return eventsService.updateEventRequests(userId, eventId, request);
    }
}
