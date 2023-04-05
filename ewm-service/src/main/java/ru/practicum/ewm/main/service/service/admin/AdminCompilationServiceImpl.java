package ru.practicum.ewm.main.service.service.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.main.service.dto.compilation.NewCompilationDto;
import ru.practicum.ewm.main.service.dto.compilation.UpdateCompilationRequest;
import ru.practicum.ewm.main.service.exception.IdNotFoundException;
import ru.practicum.ewm.main.service.mapper.CompilationMapper;
import ru.practicum.ewm.main.service.model.Compilation;
import ru.practicum.ewm.main.service.repository.CompilationsRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminCompilationServiceImpl implements AdminCompilationService {

    private final CompilationsRepository compilationsRepository;
    private final CompilationMapper compilationMapper;

    @Override
    public Compilation updateCompilation(long compId, UpdateCompilationRequest compilationDto) {
        Compilation compilation = compilationsRepository.findById(compId)
                .orElseThrow(() -> new IdNotFoundException("Compilation with id = " + compId + " not found"));
        compilationMapper.updateCompilationFromUpdateCompilationRequest(compilationDto, compilation);
        Compilation result = compilationsRepository.save(compilation);
        log.info("Compilation with id = {} updated", result.getId());
        return result;
    }

    @Override
    public void deleteCompilation(long compId) {
        if (!compilationsRepository.existsById(compId)) {
            throw new IdNotFoundException("Compilation with id = " + compId + " not found");
        }
        compilationsRepository.deleteById(compId);
        log.info("Compilation with id = {} deleted", compId);
    }

    @Override
    public Compilation addCompilation(NewCompilationDto compilationDto) {
        Compilation result = compilationsRepository.save(compilationMapper.toCompilation(compilationDto));
        log.info("Compilation with id = {} created", result.getId());
        return result;
    }
}
