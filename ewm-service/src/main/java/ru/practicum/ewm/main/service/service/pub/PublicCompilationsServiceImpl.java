package ru.practicum.ewm.main.service.service.pub;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.main.service.dto.compilation.CompilationDto;
import ru.practicum.ewm.main.service.exception.EntityNotFoundException;
import ru.practicum.ewm.main.service.mapper.CompilationMapper;
import ru.practicum.ewm.main.service.repository.CompilationsRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicCompilationsServiceImpl implements PublicCompilationsService {

    private final CompilationsRepository compilationsRepository;
    private final CompilationMapper compilationMapper;

    @Override
    public List<CompilationDto> getCompilations(Boolean pinned, Pageable pageable) {
        return compilationMapper.toCompilationDtos(pinned != null ? compilationsRepository.findByPinned(pinned, pageable)
                : compilationsRepository.findAll(pageable).toList());
    }

    @Override
    public CompilationDto getCompilationById(long compId) {
        return compilationMapper.toCompilationDto(compilationsRepository.findById(compId)
                .orElseThrow((() -> new EntityNotFoundException("Compilation with id = " + compId + " not found"))));
    }
}
