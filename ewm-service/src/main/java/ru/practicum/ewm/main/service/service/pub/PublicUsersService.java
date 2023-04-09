package ru.practicum.ewm.main.service.service.pub;

import org.springframework.data.domain.Pageable;
import ru.practicum.ewm.main.service.dto.user.UserShortDto;

import java.util.List;

public interface PublicUsersService {
    List<UserShortDto> getMostPopularInitiators(Pageable pageable);
}
