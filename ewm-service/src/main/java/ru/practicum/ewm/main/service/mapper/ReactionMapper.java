package ru.practicum.ewm.main.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.ewm.main.service.dto.reaction.ReactionDto;
import ru.practicum.ewm.main.service.model.Reaction;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReactionMapper {

    @Mapping(target = "reviewer", source = "reaction.reviewer.id")
    @Mapping(target = "event", source = "reaction.event.id")
    ReactionDto toReactionDto(Reaction reaction);

    List<ReactionDto> toReactionDtos(List<Reaction> reactions);
}
