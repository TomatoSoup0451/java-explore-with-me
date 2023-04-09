package ru.practicum.ewm.main.service.mapper;

import org.mapstruct.*;
import ru.practicum.ewm.main.service.dto.compilation.CompilationDto;
import ru.practicum.ewm.main.service.dto.compilation.NewCompilationDto;
import ru.practicum.ewm.main.service.dto.compilation.UpdateCompilationRequest;
import ru.practicum.ewm.main.service.model.Compilation;
import ru.practicum.ewm.main.service.model.Event;
import ru.practicum.ewm.main.service.repository.EventsRepository;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface CompilationMapper {

    CompilationDto toCompilationDto(Compilation compilation);

    List<CompilationDto> toCompilationDtos(List<Compilation> compilation);

    @Mapping(target = "events", source = "events", qualifiedByName = "events")
    Compilation toCompilation(NewCompilationDto dto, @Context EventsRepository repository);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "events", source = "events", qualifiedByName = "events")
    void updateCompilationFromUpdateCompilationRequest(UpdateCompilationRequest request,
                                                       @MappingTarget Compilation compilation,
                                                       @Context EventsRepository repository);

    @Named("events")
    default Set<Event> setEvents(@Context EventsRepository repository, Set<Long> eventIds) {
        return repository.findAllByIdIn(eventIds);
    }
}
