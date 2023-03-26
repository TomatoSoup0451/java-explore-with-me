package ru.practicum.ewm.stat.service.service;

import ru.practicum.ewm.stat.dto.EndpointHitDto;
import ru.practicum.ewm.stat.dto.EndpointStatsDto;
import ru.practicum.ewm.stat.service.model.EndpointHit;

import java.util.List;

public interface StatsService {
    EndpointHit addHit(EndpointHitDto endpointHitDto);

    List<EndpointStatsDto> getStats(String start, String end, List<String> uris, boolean unique);
}
