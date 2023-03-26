package ru.practicum.ewm.stat.client.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.stat.client.client.StatsClient;
import ru.practicum.ewm.stat.dto.EndpointHitDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StatsController {
    private final StatsClient statsClient;

    @PostMapping("/hit")
    public ResponseEntity<Object> addHit(@RequestBody EndpointHitDto endpointHitDto) {
        return statsClient.addHit(endpointHitDto);
    }

    @GetMapping("/stats")
    public ResponseEntity<Object> getStats(@RequestParam String start,
                                           @RequestParam String end,
                                           @RequestParam(required = false) List<String> uris,
                                           @RequestParam(defaultValue = "false") boolean unique) {
        return statsClient.getStats(start, end, uris, unique);
    }
}
