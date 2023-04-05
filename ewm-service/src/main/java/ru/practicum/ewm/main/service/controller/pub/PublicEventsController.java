package ru.practicum.ewm.main.service.controller.pub;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main.service.dto.event.EventFullDto;
import ru.practicum.ewm.main.service.dto.event.EventShortDto;
import ru.practicum.ewm.main.service.service.pub.PublicEventsService;
import ru.practicum.ewm.stat.client.StatsClient;
import ru.practicum.ewm.stat.dto.EndpointHitDto;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import static ru.practicum.ewm.main.service.util.Globals.getPageable;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/events")
@ComponentScan(basePackages = {"ru.practicum.ewm.stat.client"})
public class PublicEventsController {
    private final PublicEventsService service;
    private final StatsClient statsClient;
    private final String app = "ewm-main-service";
    private final String datePattern = "yyyy-MM-dd HH:mm:ss";
    private final SimpleDateFormat formatter = new SimpleDateFormat(datePattern);


    @GetMapping
    public List<EventShortDto> getEventsByParams(@RequestParam(required = false) String text,
                                                 @RequestParam(required = false) List<Long> categories,
                                                 @RequestParam(required = false) Boolean paid,
                                                 @RequestParam(required = false) String rangeStart,
                                                 @RequestParam(required = false) String rangeEnd,
                                                 @RequestParam(required = false) Boolean onlyAvailable,
                                                 @RequestParam(required = false) String sort,
                                                 @RequestParam(defaultValue = "0") int from,
                                                 @RequestParam(defaultValue = "10") int size,
                                                 HttpServletRequest request) {
        List<EventShortDto> result = service.getEventsByParams(text, categories, paid, rangeStart, rangeEnd, onlyAvailable,
                sort, getPageable(from, size));
        statsClient.addHit(EndpointHitDto.builder()
                .app(app)
                .uri(request.getRequestURI())
                .ip(request.getRemoteAddr())
                .timestamp(formatter.format(Date.from(Instant.now())))
                .build());
        return result;
    }

    @GetMapping(path = "/{id}")
    public EventFullDto getEventById(@PathVariable long id, HttpServletRequest request) {
        EventFullDto result = service.getEventById(id);
        statsClient.addHit(EndpointHitDto.builder()
                .app(app)
                .uri(request.getRequestURI())
                .ip(request.getRemoteAddr())
                .timestamp(formatter.format(Date.from(Instant.now())))
                .build());
        return result;
    }
}
