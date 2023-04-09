package ru.practicum.ewm.main.service.dto.reaction;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Value
@Builder
@Jacksonized
public class ReactionDto {
    Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date created;

    @NotNull
    boolean positive;

    @NotNull
    Long reviewer;

    @NotNull
    Long event;
}
