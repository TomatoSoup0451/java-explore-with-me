package ru.practicum.ewm.main.service.controller.priv;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main.service.dto.reaction.ReactionDto;
import ru.practicum.ewm.main.service.service.priv.PrivateReactionsService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/reactions")
public class PrivateReactionsController {
    private final PrivateReactionsService reactionsService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ReactionDto addReaction(@PathVariable long userId,
                                   @RequestParam long eventId,
                                   @RequestParam boolean like) {
        return reactionsService.addReaction(userId, eventId, like);
    }

    @PatchMapping(path = "/{reactionId}")
    public ReactionDto updateReaction(@PathVariable long userId,
                                      @PathVariable long reactionId,
                                      @RequestParam boolean like) {
        return reactionsService.updateReaction(userId, reactionId, like);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/{reactionId}")
    public void deleteReaction(@PathVariable long userId, @PathVariable long reactionId) {
        reactionsService.deleteReaction(userId, reactionId);
    }

    @GetMapping
    public List<ReactionDto> getAllReactions(@PathVariable long userId) {
        return reactionsService.getAllReactions(userId);
    }

}
