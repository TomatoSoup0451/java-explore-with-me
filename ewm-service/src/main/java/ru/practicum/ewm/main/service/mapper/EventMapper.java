package ru.practicum.ewm.main.service.mapper;

import org.mapstruct.*;
import org.springframework.context.annotation.ComponentScan;
import ru.practicum.ewm.main.service.dto.event.*;
import ru.practicum.ewm.main.service.exception.EntityNotFoundException;
import ru.practicum.ewm.main.service.model.Category;
import ru.practicum.ewm.main.service.model.Event;
import ru.practicum.ewm.main.service.model.enums.ParticipationStatus;
import ru.practicum.ewm.main.service.repository.CategoriesRepository;
import ru.practicum.ewm.main.service.repository.ParticipationRequestsRepository;
import ru.practicum.ewm.main.service.repository.ReactionsRepository;
import ru.practicum.ewm.stat.client.StatsClient;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
@ComponentScan(basePackages = {"ru.practicum.ewm.stat.client"})
public interface EventMapper {

    List<EventShortDto> toEventShortDtos(List<Event> events,
                                         @Context ParticipationRequestsRepository requestsRepository,
                                         @Context ReactionsRepository reactionsRepository,
                                         @Context StatsClient client);


    @Mapping(target = "views", source = "event", qualifiedByName = "views")
    @Mapping(target = "confirmedRequests", source = "id", qualifiedByName = "confirmed_requests")
    @Mapping(target = "rating", source = "id", qualifiedByName = "rating")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    EventShortDto toEventShortDto(Event event,
                                  @Context ParticipationRequestsRepository requestsRepository,
                                  @Context ReactionsRepository reactionsRepository,
                                  @Context StatsClient client);

    @Mapping(target = "eventDate", source = "eventDto.eventDate",
            dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "category", source = "categoryId", qualifiedByName = "category")
    @Mapping(target = "participantLimit", source = "eventDto.participantLimit", defaultValue = "0")
    Event toEvent(NewEventDto eventDto,
                  @Context CategoriesRepository categoriesRepository);

    @Mapping(target = "views", source = "event", qualifiedByName = "views")
    @Mapping(target = "confirmedRequests", source = "id", qualifiedByName = "confirmed_requests")
    @Mapping(target = "rating", source = "id", qualifiedByName = "rating")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    EventFullDto toEventFullDto(Event event,
                                @Context ParticipationRequestsRepository requestsRepository,
                                @Context ReactionsRepository reactionsRepository,
                                @Context StatsClient client);

    List<EventFullDto> toEventFullDtos(List<Event> event,
                                       @Context ParticipationRequestsRepository requestsRepository,
                                       @Context ReactionsRepository reactionsRepository,
                                       @Context StatsClient client);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "category", source = "categoryId", qualifiedByName = "category")
    @Mapping(target = "eventDate", source = "request.eventDate",
            dateFormat = "yyyy-MM-dd HH:mm:ss")
    void updateEventFromUpdateEventUserRequest(UpdateEventUserRequest request, @MappingTarget Event event,
                                               @Context CategoriesRepository categoriesRepository);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "category", source = "categoryId", qualifiedByName = "category")
    @Mapping(target = "eventDate", source = "request.eventDate",
            dateFormat = "yyyy-MM-dd HH:mm:ss")
    void updateEventFromUpdateEventAdminRequest(UpdateEventAdminRequest request,
                                                @MappingTarget Event event,
                                                @Context CategoriesRepository categoriesRepository);

    @Named("category")
    default Category setCategory(@Context CategoriesRepository repository, Long categoryId) {
        return repository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category with id = " + categoryId + "not found"));
    }

    @Named("confirmed_requests")
    default Long setConfirmedRequests(@Context ParticipationRequestsRepository requestsRepository, Long eventId) {
        return requestsRepository.countByEventIdAndStatus(eventId, ParticipationStatus.CONFIRMED);
    }

    @Named("rating")
    default Integer setRating(@Context ReactionsRepository reactionsRepository, Long eventId) {
        return reactionsRepository.countRatingByEventId(eventId);
    }

    @Named("views")
    default Integer setViews(@Context StatsClient client, Event event) {
        return event.getPublishedOn() != null ? ((List) (client.getStats(event.getPublishedOn().toString(),
                Instant.now().toString(),
                Collections.singletonList("/events/" + event.getId()),
                false)).getBody()).size() : null;
    }
}
