package ru.practicum.ewm.main.service.service.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.main.service.dto.user.NewUserRequest;
import ru.practicum.ewm.main.service.dto.user.UserDto;
import ru.practicum.ewm.main.service.exception.IdNotFoundException;
import ru.practicum.ewm.main.service.mapper.UserMapper;
import ru.practicum.ewm.main.service.model.User;
import ru.practicum.ewm.main.service.repository.UsersRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminUsersServiceImpl implements AdminUsersService {
    private final UsersRepository usersRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserDto> getUsers(List<Long> ids, Pageable pageable) {
        return userMapper.toUserDtos(ids == null ? usersRepository.findAll(pageable).toList()
                : usersRepository.findAllByIdIn(ids, pageable));
    }

    @Override
    public User addUser(NewUserRequest userDto) {
        User result = usersRepository.save(userMapper.toUser(userDto));
        log.info("User with id = {} added", result.getId());
        return result;
    }

    @Override
    public void deleteUser(long userId) {
        if (!usersRepository.existsById(userId)) {
            throw new IdNotFoundException("User with id = " + userId + " not found");
        }
        usersRepository.deleteById(userId);
        log.info("User with id = {} deleted", userId);
    }
}
