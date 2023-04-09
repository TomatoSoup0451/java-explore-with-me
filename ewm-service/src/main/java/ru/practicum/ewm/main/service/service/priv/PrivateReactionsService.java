package ru.practicum.ewm.main.service.service.priv;

import ru.practicum.ewm.main.service.dto.reaction.ReactionDto;

import java.util.List;

public interface PrivateReactionsService {
    ReactionDto addReaction(long userId, long eventId, boolean like);

    ReactionDto updateReaction(long userId, long reactionId, boolean like);

    void deleteReaction(long userId, long reactionId);

    List<ReactionDto> getAllReactions(long userId);
}
