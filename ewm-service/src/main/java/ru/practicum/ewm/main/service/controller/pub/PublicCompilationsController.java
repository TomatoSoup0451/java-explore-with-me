package ru.practicum.ewm.main.service.controller.pub;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main.service.dto.compilation.CompilationDto;
import ru.practicum.ewm.main.service.service.pub.PublicCompilationsService;

import java.util.List;

import static ru.practicum.ewm.main.service.util.Globals.getPageable;

@RestController
@RequestMapping("/compilations")
@RequiredArgsConstructor
public class PublicCompilationsController {
    private final PublicCompilationsService compilationsService;

    @GetMapping
    public List<CompilationDto> getCompilations(@RequestParam(required = false) Boolean pinned,
                                                @RequestParam(defaultValue = "0") int from,
                                                @RequestParam(defaultValue = "10") int to) {
        return compilationsService.getCompilations(pinned, getPageable(from, to));
    }

    @GetMapping(path = "/{compId}")
    public CompilationDto getCompilationById(@PathVariable long compId) {
        return compilationsService.getCompilationById(compId);
    }
}
