package ru.practicum.ewm.main.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.practicum.ewm.main.service.dto.user.NewUserRequest;
import ru.practicum.ewm.main.service.dto.user.UserDto;
import ru.practicum.ewm.main.service.dto.user.UserShortDto;
import ru.practicum.ewm.main.service.model.User;
import ru.practicum.ewm.main.service.repository.ReactionsRepository;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Autowired
    ReactionsRepository reactionsRepository;

    @Mapping(target = "rating", expression = "java(reactionsRepository.countRatingByInitiatorId(user.getId()))")
    public abstract UserDto toUserDto(User user);

    public abstract List<UserDto> toUserDtos(List<User> user);

    @Mapping(target = "rating", expression = "java(reactionsRepository.countRatingByInitiatorId(user.getId()))")
    public abstract UserShortDto toUserShortDto(User user);

    public abstract User toUser(NewUserRequest userDto);

    public abstract List<UserShortDto> toUserShortDtos(List<User> allSortedByRating);
}
