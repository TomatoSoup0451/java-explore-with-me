package ru.practicum.ewm.main.service.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main.service.dto.compilation.NewCompilationDto;
import ru.practicum.ewm.main.service.dto.compilation.UpdateCompilationRequest;
import ru.practicum.ewm.main.service.model.Compilation;
import ru.practicum.ewm.main.service.service.admin.AdminCompilationService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/compilations")
public class AdminCompilationsController {
    private final AdminCompilationService service;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Compilation addCompilation(@RequestBody @Valid NewCompilationDto compilationDto) {
        return service.addCompilation(compilationDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/{compId}")
    public void deleteCompilation(@PathVariable long compId) {
        service.deleteCompilation(compId);
    }

    @PatchMapping(path = "/{compId}")
    public Compilation updateCompilation(@PathVariable long compId,
                                         @RequestBody @Valid UpdateCompilationRequest compilationDto) {
        return service.updateCompilation(compId, compilationDto);
    }

}
