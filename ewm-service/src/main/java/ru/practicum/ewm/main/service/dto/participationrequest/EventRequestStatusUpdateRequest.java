package ru.practicum.ewm.main.service.dto.participationrequest;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class EventRequestStatusUpdateRequest {
    List<Long> requestIds;
    String status;
}
