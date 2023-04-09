package ru.practicum.ewm.main.service.service.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.main.service.dto.compilation.CompilationDto;
import ru.practicum.ewm.main.service.dto.compilation.NewCompilationDto;
import ru.practicum.ewm.main.service.dto.compilation.UpdateCompilationRequest;
import ru.practicum.ewm.main.service.exception.EntityNotFoundException;
import ru.practicum.ewm.main.service.mapper.CompilationMapper;
import ru.practicum.ewm.main.service.model.Compilation;
import ru.practicum.ewm.main.service.repository.CompilationsRepository;
import ru.practicum.ewm.main.service.repository.EventsRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminCompilationServiceImpl implements AdminCompilationService {

    private final CompilationsRepository compilationsRepository;
    private final CompilationMapper compilationMapper;
    private final EventsRepository eventsRepository;

    @Override
    public CompilationDto updateCompilation(long compId, UpdateCompilationRequest compilationDto) {
        Compilation compilation = compilationsRepository.findById(compId)
                .orElseThrow(() -> new EntityNotFoundException("Compilation with id = " + compId + " not found"));
        compilationMapper.updateCompilationFromUpdateCompilationRequest(compilationDto, compilation, eventsRepository);
        Compilation result = compilationsRepository.save(compilation);
        log.info("Compilation with id = {} updated", result.getId());
        return compilationMapper.toCompilationDto(result);
    }

    @Override
    public void deleteCompilation(long compId) {
        if (!compilationsRepository.existsById(compId)) {
            throw new EntityNotFoundException("Compilation with id = " + compId + " not found");
        }
        compilationsRepository.deleteById(compId);
        log.info("Compilation with id = {} deleted", compId);
    }

    @Override
    public CompilationDto addCompilation(NewCompilationDto compilationDto) {
        Compilation result = compilationsRepository.save(compilationMapper.toCompilation(compilationDto, eventsRepository));
        log.info("Compilation with id = {} created", result.getId());
        return compilationMapper.toCompilationDto(result);
    }
}
