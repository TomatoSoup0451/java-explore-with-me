package ru.practicum.ewm.main.service.service.priv;

import ru.practicum.ewm.main.service.dto.reaction.ReactionDto;

import java.util.List;

public interface PrivateReactionsService {
    ReactionDto addReaction(long userId, long eventId, boolean positive);

    ReactionDto updateReaction(long userId, long reactionId, boolean positive);

    void deleteReaction(long userId, long reactionId);

    List<ReactionDto> getAllReactions(long userId);
}
