package ru.practicum.ewm.main.service.service.pub;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.main.service.dto.user.UserShortDto;
import ru.practicum.ewm.main.service.mapper.UserMapper;
import ru.practicum.ewm.main.service.repository.UsersRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicUsersServiceImpl implements PublicUsersService {
    private final UsersRepository usersRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserShortDto> getMostPopularInitiators(Pageable pageable) {
        return userMapper.toUserShortDtos(usersRepository.findAllSortedByRating(pageable));
    }
}
