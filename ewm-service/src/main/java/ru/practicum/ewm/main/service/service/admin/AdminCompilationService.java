package ru.practicum.ewm.main.service.service.admin;

import ru.practicum.ewm.main.service.dto.compilation.CompilationDto;
import ru.practicum.ewm.main.service.dto.compilation.NewCompilationDto;
import ru.practicum.ewm.main.service.dto.compilation.UpdateCompilationRequest;

public interface AdminCompilationService {
    CompilationDto updateCompilation(long compId, UpdateCompilationRequest compilationDto);

    void deleteCompilation(long compId);

    CompilationDto addCompilation(NewCompilationDto compilationDto);
}
