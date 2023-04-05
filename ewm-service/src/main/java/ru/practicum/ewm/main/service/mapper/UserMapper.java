package ru.practicum.ewm.main.service.mapper;

import org.mapstruct.Mapper;
import ru.practicum.ewm.main.service.dto.user.NewUserRequest;
import ru.practicum.ewm.main.service.dto.user.UserDto;
import ru.practicum.ewm.main.service.dto.user.UserShortDto;
import ru.practicum.ewm.main.service.model.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toUserDto(User user);

    List<UserDto> toUserDtos(List<User> user);

    UserShortDto toUserShortDto(User user);

    User toUser(NewUserRequest userDto);

}
