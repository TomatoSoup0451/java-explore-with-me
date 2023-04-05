package ru.practicum.ewm.main.service.dto.compilation;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.Set;

@Jacksonized
@Value
@Builder
public class UpdateCompilationRequest {
    Set<Long> events;
    Boolean pinned;
    String title;
}
