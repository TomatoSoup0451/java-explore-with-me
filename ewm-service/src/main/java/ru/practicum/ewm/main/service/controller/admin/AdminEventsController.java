package ru.practicum.ewm.main.service.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main.service.dto.event.EventFullDto;
import ru.practicum.ewm.main.service.dto.event.UpdateEventAdminRequest;
import ru.practicum.ewm.main.service.service.admin.AdminEventsService;

import javax.validation.Valid;
import java.util.List;

import static ru.practicum.ewm.main.service.util.Globals.getPageable;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/events")
public class AdminEventsController {
    private final AdminEventsService adminEventsService;

    @GetMapping
    public List<EventFullDto> getEventsByParameters(@RequestParam(required = false) List<Long> users,
                                                    @RequestParam(required = false) List<String> states,
                                                    @RequestParam(required = false) List<Long> categories,
                                                    @RequestParam(required = false) String rangeStart,
                                                    @RequestParam(required = false) String rangeEnd,
                                                    @RequestParam(defaultValue = "0") int from,
                                                    @RequestParam(defaultValue = "10") int size) {
        return adminEventsService.getEventsByParameters(users, states, categories,
                rangeStart, rangeEnd, getPageable(from, size));
    }

    @PatchMapping(path = "/{eventId}")
    public EventFullDto updateEvent(@PathVariable long eventId, @RequestBody @Valid UpdateEventAdminRequest eventDto) {
        return adminEventsService.updateEvent(eventId, eventDto);
    }
}
