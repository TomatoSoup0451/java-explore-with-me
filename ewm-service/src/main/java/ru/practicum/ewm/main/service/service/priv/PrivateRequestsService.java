package ru.practicum.ewm.main.service.service.priv;

import ru.practicum.ewm.main.service.dto.participationrequest.ParticipationRequestDto;

import java.util.List;

public interface PrivateRequestsService {
    List<ParticipationRequestDto> getUserRequests(long userId);

    ParticipationRequestDto addRequest(long userId, long eventId);

    ParticipationRequestDto cancelRequest(long userId, long requestId);
}
