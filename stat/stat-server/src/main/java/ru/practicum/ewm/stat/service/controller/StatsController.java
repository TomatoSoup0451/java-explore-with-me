package ru.practicum.ewm.stat.service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.stat.dto.EndpointHitDto;
import ru.practicum.ewm.stat.dto.EndpointStatsDto;
import ru.practicum.ewm.stat.service.model.EndpointHit;
import ru.practicum.ewm.stat.service.service.StatsService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StatsController {

    private final StatsService statsService;

    @PostMapping("/hit")
    public EndpointHit addHit(@RequestBody EndpointHitDto endpointHitDto) {
        return statsService.addHit(endpointHitDto);
    }

    @GetMapping("/stats")
    public List<EndpointStatsDto> getStats(@RequestParam String start,
                                           @RequestParam String end,
                                           @RequestParam(required = false) List<String> uris,
                                           @RequestParam(defaultValue = "false") boolean unique) {
        return statsService.getStats(start, end, uris, unique);
    }
}
