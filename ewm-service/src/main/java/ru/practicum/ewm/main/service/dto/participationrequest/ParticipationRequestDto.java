package ru.practicum.ewm.main.service.dto.participationrequest;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class ParticipationRequestDto {
    Long id;

    String created;

    String status;

    Long requester;

    Long event;
}
