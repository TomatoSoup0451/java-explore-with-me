package ru.practicum.ewm.main.service.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.ewm.main.service.model.Location;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Jacksonized
@Builder
@Value
public class NewEventDto {
    @NotBlank
    String annotation;
    @NotBlank
    String description;
    @NotNull
    @JsonProperty("category")
    Long categoryId;
    @NotNull
    Location location;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date eventDate;

    Boolean paid;

    Boolean requestModeration;
    @NotBlank
    String title;

    Integer participantLimit;

}
