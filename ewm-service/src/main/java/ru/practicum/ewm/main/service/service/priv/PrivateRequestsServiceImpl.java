package ru.practicum.ewm.main.service.service.priv;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.main.service.dto.participationrequest.ParticipationRequestDto;
import ru.practicum.ewm.main.service.exception.EntityNotFoundException;
import ru.practicum.ewm.main.service.mapper.ParticipationRequestMapper;
import ru.practicum.ewm.main.service.model.Event;
import ru.practicum.ewm.main.service.model.ParticipationRequest;
import ru.practicum.ewm.main.service.model.User;
import ru.practicum.ewm.main.service.model.enums.EventState;
import ru.practicum.ewm.main.service.model.enums.ParticipationStatus;
import ru.practicum.ewm.main.service.repository.EventsRepository;
import ru.practicum.ewm.main.service.repository.ParticipationRequestsRepository;
import ru.practicum.ewm.main.service.repository.UsersRepository;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrivateRequestsServiceImpl implements PrivateRequestsService {
    private final ParticipationRequestsRepository requestRepository;
    private final ParticipationRequestMapper requestMapper;
    private final EventsRepository eventsRepository;
    private final UsersRepository usersRepository;

    @Override
    public List<ParticipationRequestDto> getUserRequests(long userId) {
        return requestMapper.toParticipationRequestsDtos(requestRepository.findAllByRequesterId(userId));
    }

    @Override
    public ParticipationRequestDto addRequest(long userId, long eventId) {
        Event event = eventsRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event with id = " + eventId + " not found"));
        User user = usersRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id = " + userId + " not found"));
        if (requestRepository.existsByRequesterIdAndEventId(userId, eventId)) {
            throw new DataIntegrityViolationException("Request from user with id = " + userId + " to event " + eventId +
                    " already exist");
        }
        if (event.getInitiator().equals(user)) {
            throw new DataIntegrityViolationException("User with id = " + userId + " is initiator to event " + eventId +
                    " and can't be participant");
        }
        if (event.getState() != EventState.PUBLISHED) {
            throw new DataIntegrityViolationException("Event with id = " + eventId +
                    " should be published, but actually was " + event.getState().name());
        }
        if (event.getParticipantLimit() != 0 &&
                event.getParticipantLimit() <= requestRepository.countByEventIdAndStatus(eventId, ParticipationStatus.CONFIRMED)) {
            throw new DataIntegrityViolationException("Event with id = " + eventId + "reached participants limit");
        }
        ParticipationRequest request = new ParticipationRequest();
        request.setEvent(event)
                .setStatus(event.getRequestModeration() ? ParticipationStatus.PENDING : ParticipationStatus.CONFIRMED)
                .setRequester(user)
                .setCreated(Date.from(Instant.now()));
        ParticipationRequestDto result = requestMapper.toParticipationRequestDto(requestRepository.save(request));
        log.info("Request with id = {} added", result.getId());
        return result;
    }

    @Override
    public ParticipationRequestDto cancelRequest(long userId, long requestId) {
        ParticipationRequest request = requestRepository.findByIdAndRequesterId(requestId, userId)
                .orElseThrow(() -> new EntityNotFoundException("Request with id = " + requestId +
                        "and userId = " + userId + " not found"));
        request.setStatus(ParticipationStatus.CANCELED);
        ParticipationRequestDto result = requestMapper.toParticipationRequestDto(requestRepository.save(request));
        log.info("Request with id = {} cancelled", result.getId());
        return result;
    }
}
