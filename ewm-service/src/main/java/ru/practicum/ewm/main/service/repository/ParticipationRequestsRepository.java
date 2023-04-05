package ru.practicum.ewm.main.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.main.service.model.ParticipationRequest;
import ru.practicum.ewm.main.service.model.enums.ParticipationStatus;

import java.util.List;
import java.util.Optional;

public interface ParticipationRequestsRepository extends JpaRepository<ParticipationRequest, Long> {
    List<ParticipationRequest> findAllByEventId(long eventId);

    List<ParticipationRequest> findAllByEventIdAndStatus(long eventId, ParticipationStatus status);

    long countByEventIdAndStatus(long eventId, ParticipationStatus status);

    List<ParticipationRequest> findAllByRequesterId(long requesterId);

    boolean existsByRequesterIdAndEventId(long requesterId, long eventId);

    Optional<ParticipationRequest> findByIdAndRequesterId(long id, long requesterId);

}
