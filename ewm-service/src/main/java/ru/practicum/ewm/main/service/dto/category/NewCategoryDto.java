package ru.practicum.ewm.main.service.dto.category;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotBlank;

@Jacksonized
@Builder
@Value
public class NewCategoryDto {
    @NotBlank
    String name;
}
