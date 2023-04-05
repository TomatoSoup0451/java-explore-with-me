package ru.practicum.ewm.main.service.dto.compilation;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.ewm.main.service.model.Event;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Jacksonized
@Builder
@Value
public class CompilationDto {
    Long id;
    @NotBlank
    String title;
    @NotNull
    Boolean pinned;
    @NotBlank
    Set<Event> events;
}
