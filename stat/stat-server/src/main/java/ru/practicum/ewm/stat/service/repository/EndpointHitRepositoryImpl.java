package ru.practicum.ewm.stat.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.ewm.stat.dto.EndpointStatsDto;
import ru.practicum.ewm.stat.service.model.EndpointHit;

import java.util.Date;
import java.util.List;

public interface EndpointHitRepositoryImpl extends JpaRepository<EndpointHit, Long> {

    @Query(" select new ru.practicum.ewm.stat.dto.EndpointStatsDto(e.app, e.uri, count (e.ip))" +
            "from EndpointHit e where e.timestamp > :start and e.timestamp < :end and e.uri in :uris " +
            "group by e.app, e.uri " +
            "order by count (e.ip) desc ")
    List<EndpointStatsDto> findByUrisBetweenStartAndEnd(@Param("start") Date start,
                                                        @Param("end") Date end,
                                                        @Param("uris") List<String> uris);

    @Query(" select new ru.practicum.ewm.stat.dto.EndpointStatsDto(e.app, e.uri, count (distinct e.ip))" +
            "from EndpointHit e where e.timestamp > :start and e.timestamp < :end and e.uri in :uris " +
            "group by e.app, e.uri " +
            "order by count (e.ip) desc ")
    List<EndpointStatsDto> findUniqueByUrisBetweenStartAndEnd(@Param("start") Date start,
                                                              @Param("end") Date end,
                                                              @Param("uris") List<String> uris);
}
