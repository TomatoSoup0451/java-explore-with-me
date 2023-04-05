package ru.practicum.ewm.main.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.ewm.main.service.dto.participationrequest.ParticipationRequestDto;
import ru.practicum.ewm.main.service.model.ParticipationRequest;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ParticipationRequestMapper {

    @Mapping(target = "requester", source = "request.requester.id")
    @Mapping(target = "event", source = "request.event.id")
    ParticipationRequestDto toParticipationRequestDto(ParticipationRequest request);

    List<ParticipationRequestDto> toParticipationRequestsDtos(List<ParticipationRequest> requests);

}
