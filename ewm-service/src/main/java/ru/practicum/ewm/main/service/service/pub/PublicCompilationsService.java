package ru.practicum.ewm.main.service.service.pub;

import org.springframework.data.domain.Pageable;
import ru.practicum.ewm.main.service.dto.compilation.CompilationDto;

import java.util.List;

public interface PublicCompilationsService {
    List<CompilationDto> getCompilations(Boolean pinned, Pageable pageable);

    CompilationDto getCompilationById(long compId);
}
