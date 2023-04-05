package ru.practicum.ewm.main.service.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.ewm.main.service.dto.category.CategoryDto;
import ru.practicum.ewm.main.service.dto.user.UserShortDto;
import ru.practicum.ewm.main.service.model.Location;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Jacksonized
@Builder
@Value
public class EventFullDto {
    long id;
    @NotBlank
    String annotation;
    @NotBlank
    CategoryDto category;

    String createdOn;

    String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull
    Date eventDate;

    @NotNull
    UserShortDto initiator;
    @NotNull
    Location location;
    @NotNull
    Boolean paid;

    Integer participantLimit;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date publishedOn;

    Boolean requestModeration;

    String state;
    @NotBlank
    String title;

    Long confirmedRequests;

    Integer views;
}
