package ru.practicum.ewm.main.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.main.service.model.Reaction;

import java.util.List;

public interface ReactionsRepository extends JpaRepository<Reaction, Long> {
    int countByEventIdAndPositive(Long eventId, Boolean positive);


    @Query("select coalesce(sum (" +
            "(case when r.positive = true then 1 else 0 end ) -" +
            "(case when r.positive = false then 1 else 0 end)), 0)" +
            "from Reaction r " +
            "where r.event.id = ?1")
    int countRatingByEventId(Long eventId);

    @Query("select coalesce(sum (" +
            "(case when r.positive = true then 1 else 0 end ) -" +
            "(case when r.positive = false then 1 else 0 end)), 0)" +
            "from Reaction r " +
            "where r.event.id in " +
            "(select e.id from Event e " +
            "where e.initiator.id = ?1)")
    int countRatingByInitiatorId(Long initiatorId);

    List<Reaction> findAllByReviewerId(Long reviewerId);

    boolean existsByReviewerId(long reviewerId);

    boolean existsByReviewerIdAndEventId(long reviewerId, long eventId);
}
