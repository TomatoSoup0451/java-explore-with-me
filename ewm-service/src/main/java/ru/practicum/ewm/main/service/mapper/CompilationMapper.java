package ru.practicum.ewm.main.service.mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.practicum.ewm.main.service.dto.compilation.CompilationDto;
import ru.practicum.ewm.main.service.dto.compilation.NewCompilationDto;
import ru.practicum.ewm.main.service.dto.compilation.UpdateCompilationRequest;
import ru.practicum.ewm.main.service.model.Compilation;
import ru.practicum.ewm.main.service.repository.EventsRepository;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CompilationMapper {

    @Autowired
    EventsRepository repository;

    public abstract CompilationDto toCompilationDto(Compilation compilation);

    public abstract List<CompilationDto> toCompilationDtos(List<Compilation> compilation);

    @Mapping(target = "events", expression = "java(repository.findAllByIdIn(dto.getEvents()))")
    public abstract Compilation toCompilation(NewCompilationDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "events", expression = "java(repository.findAllByIdIn(request.getEvents()))")
    public abstract void updateCompilationFromUpdateCompilationRequest(UpdateCompilationRequest request,
                                                                       @MappingTarget Compilation compilation);
}
