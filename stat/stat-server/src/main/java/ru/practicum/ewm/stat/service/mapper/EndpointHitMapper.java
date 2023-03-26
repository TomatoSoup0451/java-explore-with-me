package ru.practicum.ewm.stat.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.ewm.stat.dto.EndpointHitDto;
import ru.practicum.ewm.stat.service.model.EndpointHit;

@Mapper(componentModel = "spring")
public interface EndpointHitMapper {
    EndpointHitDto endpointHitToEndpointHitDto(EndpointHit endpointHit);

    @Mapping(target = "timestamp", source = "endpointHitDto.timestamp",
            dateFormat = "yyyy-MM-dd HH:mm:ss")
    EndpointHit endpointHitDtoToEndpointHit(EndpointHitDto endpointHitDto);
}
