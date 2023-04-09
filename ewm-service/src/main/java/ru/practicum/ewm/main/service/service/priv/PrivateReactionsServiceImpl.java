package ru.practicum.ewm.main.service.service.priv;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.main.service.dto.reaction.ReactionDto;
import ru.practicum.ewm.main.service.exception.EntityNotFoundException;
import ru.practicum.ewm.main.service.mapper.ReactionMapper;
import ru.practicum.ewm.main.service.model.Event;
import ru.practicum.ewm.main.service.model.Reaction;
import ru.practicum.ewm.main.service.model.User;
import ru.practicum.ewm.main.service.model.enums.EventState;
import ru.practicum.ewm.main.service.repository.EventsRepository;
import ru.practicum.ewm.main.service.repository.ReactionsRepository;
import ru.practicum.ewm.main.service.repository.UsersRepository;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class PrivateReactionsServiceImpl implements PrivateReactionsService {
    private final ReactionsRepository reactionsRepository;
    private final EventsRepository eventsRepository;
    private final UsersRepository usersRepository;
    private final ReactionMapper mapper;

    @Override
    public ReactionDto addReaction(long userId, long eventId, boolean like) {
        Event event = eventsRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event with id = " + eventId + " not found"));
        if (event.getState() != EventState.PUBLISHED) {
            throw new DataIntegrityViolationException("Can't add reaction to unpublished event");
        }
        User reviewer = usersRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id = " + userId + " not found"));
        if (event.getInitiator().getId() == userId) {
            throw new DataIntegrityViolationException("User with id = " + userId + " can't sent " +
                    "reactions to their own event");
        }
        if (reactionsRepository.existsByReviewerIdAndEventId(userId, eventId)) {
            throw new DataIntegrityViolationException("User with id = " + userId + " already reviewed event" +
                    " with id = " + eventId);
        }
        Reaction reaction = new Reaction();
        reaction.setEvent(event)
                .setReviewer(reviewer)
                .setLike(like)
                .setCreated(Date.from(Instant.now()));
        ReactionDto result = mapper.toReactionDto(reactionsRepository.save(reaction));
        log.info("Reaction with id = {} created", result.getId());
        return result;
    }

    @Override
    public ReactionDto updateReaction(long userId, long reactionId, boolean like) {
        Reaction reaction = reactionsRepository.findById(reactionId)
                .orElseThrow(() -> new EntityNotFoundException("Reaction with id = " + reactionId + " not found"));
        if (reaction.getReviewer().getId() != userId) {
            throw new DataIntegrityViolationException("User with id = " + userId + " can't modify reaction of user " +
                    "with id = " + reaction.getReviewer().getId());
        }
        if (reaction.isLike() == like) {
            throw new DataIntegrityViolationException("Old and new reactions can't be the same");
        }
        reaction.setLike(like).setCreated(Date.from(Instant.now()));
        ReactionDto result = mapper.toReactionDto(reactionsRepository.save(reaction));
        log.info("Reaction with id = {} updated", result.getId());
        return result;
    }

    @Override
    public void deleteReaction(long userId, long reactionId) {
        Reaction reaction = reactionsRepository.findById(reactionId)
                .orElseThrow(() -> new EntityNotFoundException("Reaction with id = " + reactionId + " not found"));
        if (reaction.getReviewer().getId() != userId) {
            throw new DataIntegrityViolationException("User with id = " + userId + " can't delete reaction of user " +
                    "with id = " + reaction.getReviewer().getId());
        }
        reactionsRepository.deleteById(reactionId);
        log.info("Reaction with id = {} deleted", reactionId);
    }

    @Override
    public List<ReactionDto> getAllReactions(long userId) {
        return mapper.toReactionDtos(reactionsRepository.findAllByReviewerId(userId));
    }
}
