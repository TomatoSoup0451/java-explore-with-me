package ru.practicum.ewm.stat.service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.stat.dto.EndpointHitDto;
import ru.practicum.ewm.stat.dto.EndpointStatsDto;
import ru.practicum.ewm.stat.service.mapper.EndpointHitMapper;
import ru.practicum.ewm.stat.service.model.EndpointHit;
import ru.practicum.ewm.stat.service.repository.EndpointHitRepositoryImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatsServiceImpl implements StatsService {

    private final EndpointHitRepositoryImpl endpointHitRepository;
    private final EndpointHitMapper endpointHitMapper;
    private final String datePattern = "yyyy-MM-dd HH:mm:ss";
    private final SimpleDateFormat formatter = new SimpleDateFormat(datePattern);

    @Override
    public EndpointHit addHit(EndpointHitDto endpointHitDto) {
        EndpointHit result = endpointHitRepository.save(endpointHitMapper.endpointHitDtoToEndpointHit(endpointHitDto));
        log.info("Endpoint hit wit id = {} added", result.getId());
        return result;
    }

    @Override
    public List<EndpointStatsDto> getStats(String start, String end, List<String> uris, boolean unique) {
        Date startDate;
        Date endDate;
        try {
            startDate = formatter.parse(start);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Illegal start date format. Pattern should be " + datePattern +
                    "but actually was " + start);
        }
        try {
            endDate = formatter.parse(end);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Illegal end date format. Pattern should be " + datePattern +
                    "but actually was " + end);
        }
        if (uris != null) {
            return unique ? endpointHitRepository.findUniqueByUrisBetweenStartAndEnd(startDate, endDate, uris) :
                    endpointHitRepository.findByUrisBetweenStartAndEnd(startDate, endDate, uris);
        } else {
            return unique ? endpointHitRepository.findAllUniqueBetweenStartAndEnd(startDate, endDate) :
                    endpointHitRepository.findAllBetweenStartAndEnd(startDate, endDate);
        }
    }
}
