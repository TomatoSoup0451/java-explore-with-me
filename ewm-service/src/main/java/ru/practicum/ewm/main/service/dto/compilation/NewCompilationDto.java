package ru.practicum.ewm.main.service.dto.compilation;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Jacksonized
@Builder
@Value
public class NewCompilationDto {
    Set<Long> events;
    Boolean pinned;
    @NotBlank
    String title;
}
