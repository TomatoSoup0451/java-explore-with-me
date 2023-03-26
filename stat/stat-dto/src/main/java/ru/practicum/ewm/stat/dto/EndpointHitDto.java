package ru.practicum.ewm.stat.dto;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class EndpointHitDto {
    String app;
    String uri;
    String ip;
    String timestamp;
}
