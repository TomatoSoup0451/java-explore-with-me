package ru.practicum.ewm.main.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.ewm.main.service.model.Reaction;

import java.util.List;

public interface ReactionsRepository extends JpaRepository<Reaction, Long> {

    @Query("select coalesce(sum (" +
            "(case when r.like = true then 1 else 0 end ) -" +
            "(case when r.like = false then 1 else 0 end)), 0)" +
            "from Reaction r " +
            "where r.event.id = :eventId")
    int countRatingByEventId(@Param("eventId") Long eventId);

    @Query("select coalesce(sum (" +
            "(case when r.like = true then 1 else 0 end ) -" +
            "(case when r.like = false then 1 else 0 end)), 0)" +
            "from Reaction r " +
            "where r.event.id in " +
            "(select e.id from Event e " +
            "where e.initiator.id = :initiatorId)")
    int countRatingByInitiatorId(@Param("initiatorId") Long initiatorId);

    List<Reaction> findAllByReviewerId(Long reviewerId);

    boolean existsByReviewerIdAndEventId(long reviewerId, long eventId);
}
