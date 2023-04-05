package ru.practicum.ewm.main.service.service.admin;

import org.springframework.data.domain.Pageable;
import ru.practicum.ewm.main.service.dto.user.NewUserRequest;
import ru.practicum.ewm.main.service.dto.user.UserDto;
import ru.practicum.ewm.main.service.model.User;

import java.util.List;

public interface AdminUsersService {
    List<UserDto> getUsers(List<Long> ids, Pageable pageable);

    User addUser(NewUserRequest userDto);

    void deleteUser(long userId);
}
