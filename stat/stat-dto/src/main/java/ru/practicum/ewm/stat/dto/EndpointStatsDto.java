package ru.practicum.ewm.stat.dto;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Builder
@Value
@RequiredArgsConstructor
public class EndpointStatsDto {
    String app;
    String uri;
    long hits;
}
