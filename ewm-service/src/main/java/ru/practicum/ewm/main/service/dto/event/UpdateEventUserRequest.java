package ru.practicum.ewm.main.service.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.ewm.main.service.model.Location;

import java.util.Date;

@Jacksonized
@Value
@Builder
public class UpdateEventUserRequest {
    String annotation;
    @JsonProperty("category")
    Long categoryId;
    String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date eventDate;
    Location location;
    Boolean paid;
    Integer participantLimit;
    Boolean requestModeration;
    String stateAction;
    String title;
}
