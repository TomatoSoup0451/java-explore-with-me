package ru.practicum.ewm.main.service.dto.reaction;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotNull;

@Value
@Builder
@Jacksonized
public class ReactionUpdateDto {
    Long id;

    @NotNull
    Boolean positive;
}
