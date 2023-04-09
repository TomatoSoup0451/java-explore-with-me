package ru.practicum.ewm.main.service.mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import ru.practicum.ewm.main.service.dto.event.*;
import ru.practicum.ewm.main.service.model.Event;
import ru.practicum.ewm.main.service.repository.CategoriesRepository;
import ru.practicum.ewm.main.service.repository.ParticipationRequestsRepository;
import ru.practicum.ewm.main.service.repository.ReactionsRepository;
import ru.practicum.ewm.stat.client.StatsClient;

import java.util.List;

@Mapper(componentModel = "spring")
@ComponentScan(basePackages = {"ru.practicum.ewm.stat.client"})
public abstract class EventMapper {
    @Autowired
    CategoriesRepository categoriesRepository;

    @Autowired
    ParticipationRequestsRepository requestsRepository;

    @Autowired
    StatsClient statsClient;

    @Autowired
    ReactionsRepository reactionsRepository;

    public abstract List<EventShortDto> toEventShortDtos(List<Event> events);


    @Mapping(target = "views", expression = "java(event.getPublishedOn() != null ? " +
            "((List)(statsClient.getStats(event.getPublishedOn().toString(), java.time.Instant.now().toString()," +
            " java.util.Collections.singletonList(\"/events/\" + event.getId()), false).getBody())).size() : null)")
    @Mapping(target = "confirmedRequests", expression = "java(requestsRepository.countByEventIdAndStatus(event.getId()," +
            " ru.practicum.ewm.main.service.model.enums.ParticipationStatus.CONFIRMED))")
    @Mapping(target = "rating", expression = "java(reactionsRepository.countRatingByEventId(event.getId()))")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract EventShortDto toEventShortDto(Event event);

    @Mapping(target = "eventDate", source = "eventDto.eventDate",
            dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "category", expression = "java(categoriesRepository.findById(eventDto.getCategory()).get())")
    @Mapping(target = "participantLimit", source = "eventDto.participantLimit", defaultValue = "0")
    public abstract Event toEvent(NewEventDto eventDto);

    @Mapping(target = "views", expression = "java(event.getPublishedOn() != null ? " +
            "((List)(statsClient.getStats(event.getPublishedOn().toString(), java.time.Instant.now().toString()," +
            " java.util.Collections.singletonList(\"/events/\" + event.getId()), false).getBody())).size() : null)")
    @Mapping(target = "confirmedRequests", expression = "java(requestsRepository.countByEventIdAndStatus(event.getId()," +
            " ru.practicum.ewm.main.service.model.enums.ParticipationStatus.CONFIRMED))")
    @Mapping(target = "rating", expression = "java(reactionsRepository.countRatingByEventId(event.getId()))")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract EventFullDto toEventFullDto(Event event);

    public abstract List<EventFullDto> toEventFullDtos(List<Event> event);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "category", expression = "java(request.getCategory()!= null ? " +
            "categoriesRepository.findById(request.getCategory()).get() :" +
            "event.getCategory())")
    @Mapping(target = "eventDate", source = "request.eventDate",
            dateFormat = "yyyy-MM-dd HH:mm:ss")
    public abstract void updateEventFromUpdateEventUserRequest(UpdateEventUserRequest request, @MappingTarget Event event);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "category", expression = "java(request.getCategory()!= null ? " +
            "categoriesRepository.findById(request.getCategory()).get() :" +
            "event.getCategory())")
    @Mapping(target = "eventDate", source = "request.eventDate",
            dateFormat = "yyyy-MM-dd HH:mm:ss")
    public abstract void updateEventFromUpdateEventAdminRequest(UpdateEventAdminRequest request, @MappingTarget Event event);
}
