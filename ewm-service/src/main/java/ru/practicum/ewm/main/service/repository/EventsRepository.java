package ru.practicum.ewm.main.service.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.ewm.main.service.model.Category;
import ru.practicum.ewm.main.service.model.Event;
import ru.practicum.ewm.main.service.model.User;
import ru.practicum.ewm.main.service.model.enums.EventState;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface EventsRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByInitiatorId(long initiatorId, Pageable pageable);

    Optional<Event> findByIdAndInitiatorId(long id, long initiatorId);

    boolean existsByIdAndInitiatorId(long eventId, long initiatorId);

    @Query("select e from Event e " +
            "where (cast(:startDate as date) is null or e.eventDate > :startDate)" +
            "and (cast(:endDate as date) is null or e.eventDate < :endDate)" +
            "and (:users is null or e.initiator in :users) " +
            "and (:categories is null or e.category in :categories) " +
            "and (:states is null or e.state in :states)")
    List<Event> findAllByParamsAdmin(@Param("users") List<User> users,
                                     @Param("states") List<EventState> states,
                                     @Param("categories") List<Category> categories,
                                     @Param("startDate") Date startDate,
                                     @Param("endDate") Date endDate,
                                     Pageable pageable);

    @Query("select e from Event e " +
            "where (:text is null or upper(e.annotation) like upper(concat('%', :text, '%'))) " +
            "and (:paid is null or e.paid = :paid)" +
            "and (:categories is null or e.category in :categories) " +
            "and (cast(:startDate as date) is null or e.eventDate > :startDate)" +
            "and (cast(:endDate as date) is null or e.eventDate < :endDate) " +
            "and (:onlyAvailable is null or :onlyAvailable = false " +
            "or e.participantLimit = 0 " +
            "or (select count (p) from ParticipationRequest p inner join p.event ev " +
            "where ev = e and p.status = 'CONFIRMED') < e.participantLimit)")
    List<Event> findAllByParamsPublic(@Param("text") String text,
                                      @Param("categories") List<Category> categories,
                                      @Param("paid") Boolean paid,
                                      @Param("startDate") Date startDate,
                                      @Param("endDate") Date endDate,
                                      @Param("onlyAvailable") Boolean onlyAvailable,
                                      Pageable pageable);

    Optional<Event> findByIdAndState(long id, EventState state);

    Set<Event> findAllByIdIn(List<Long> id);

    Set<Event> findAllByIdIn(Set<Long> id);
}
