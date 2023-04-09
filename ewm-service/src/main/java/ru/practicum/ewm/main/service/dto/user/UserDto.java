package ru.practicum.ewm.main.service.dto.user;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Builder
@Value
@Jacksonized
public class UserDto {
    Long id;
    @NotBlank
    String name;
    @NotBlank @Email
    String email;

    Integer rating;
}
