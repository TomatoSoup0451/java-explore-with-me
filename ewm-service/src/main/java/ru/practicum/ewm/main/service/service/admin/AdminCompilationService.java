package ru.practicum.ewm.main.service.service.admin;

import ru.practicum.ewm.main.service.dto.compilation.NewCompilationDto;
import ru.practicum.ewm.main.service.dto.compilation.UpdateCompilationRequest;
import ru.practicum.ewm.main.service.model.Compilation;

public interface AdminCompilationService {
    Compilation updateCompilation(long compId, UpdateCompilationRequest compilationDto);

    void deleteCompilation(long compId);

    Compilation addCompilation(NewCompilationDto compilationDto);
}
