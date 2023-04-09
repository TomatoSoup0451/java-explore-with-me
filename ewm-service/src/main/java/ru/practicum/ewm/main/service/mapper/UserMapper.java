package ru.practicum.ewm.main.service.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.practicum.ewm.main.service.dto.user.NewUserRequest;
import ru.practicum.ewm.main.service.dto.user.UserDto;
import ru.practicum.ewm.main.service.dto.user.UserShortDto;
import ru.practicum.ewm.main.service.model.User;
import ru.practicum.ewm.main.service.repository.ReactionsRepository;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "rating", source = "id", qualifiedByName = "rating")
    UserDto toUserDto(User user, @Context ReactionsRepository reactionsRepository);

    List<UserDto> toUserDtos(List<User> user, @Context ReactionsRepository reactionsRepository);

    @Mapping(target = "rating", source = "id", qualifiedByName = "rating")
    UserShortDto toUserShortDto(User user, @Context ReactionsRepository reactionsRepository);

    User toUser(NewUserRequest userDto);

    List<UserShortDto> toUserShortDtos(List<User> allSortedByRating, @Context ReactionsRepository reactionsRepository);

    @Named("rating")
    default Integer setRating(@Context ReactionsRepository reactionsRepository, Long userId) {
        return reactionsRepository.countRatingByInitiatorId(userId);
    }
}
