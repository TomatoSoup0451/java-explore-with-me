package ru.practicum.ewm.main.service.controller.pub;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.main.service.dto.user.UserShortDto;
import ru.practicum.ewm.main.service.service.pub.PublicUsersService;

import java.util.List;

import static ru.practicum.ewm.main.service.util.Globals.getPageable;

@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
public class PublicUsersController {
    private final PublicUsersService usersService;

    @GetMapping
    public List<UserShortDto> getMostPopularInitiators(@RequestParam(defaultValue = "0") int from,
                                                       @RequestParam(defaultValue = "10") int size) {
        return usersService.getMostPopularInitiators(getPageable(from, size));
    }
}
