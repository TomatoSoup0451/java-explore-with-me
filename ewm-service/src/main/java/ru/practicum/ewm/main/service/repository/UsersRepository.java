package ru.practicum.ewm.main.service.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.main.service.model.User;

import java.util.List;

public interface UsersRepository extends JpaRepository<User, Long> {
    List<User> findAllByIdIn(List<Long> ids, Pageable pageable);

    @Query(value = "select u.*, sum ((case when r.positive = true then 1 else 0 end ) - " +
            "(case when r.positive = false then 1 else 0 end)) as rating " +
            "from users as u " +
            "inner join events e on u.id = e.initiator_id " +
            "inner join reactions r on e.id = r.event_id " +
            "where r.positive is not null " +
            "group by u.id " +
            "order by rating desc", nativeQuery = true)
    List<User> findAllSortedByRating(Pageable pageable);
}
