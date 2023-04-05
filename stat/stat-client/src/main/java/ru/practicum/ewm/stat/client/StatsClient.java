package ru.practicum.ewm.stat.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;
import ru.practicum.ewm.stat.dto.EndpointHitDto;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class StatsClient extends BaseClient {

    @Autowired
    public StatsClient(@Value("${stats-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> addHit(EndpointHitDto endpointHitDto) {
        return post("/hit", endpointHitDto);
    }

    public ResponseEntity<Object> getStats(String start, String end, List<String> uris, boolean unique) {
        String path = UriComponentsBuilder.newInstance()
                .path("/stats/")
                .queryParam("start", URLEncoder.encode(start, StandardCharsets.UTF_8).replace("%3A", ":"))
                .queryParam("end", URLEncoder.encode(start, StandardCharsets.UTF_8).replace("%3A", ":"))
                .queryParam("uris", uris)
                .queryParam("unique", unique)
                .toUriString();
        return get(path);
    }

    public ResponseEntity<Object> getStats(String start, String end, List<String> uris) {
        String path = UriComponentsBuilder.newInstance()
                .path("/stats/")
                .queryParam("start", URLEncoder.encode(start, StandardCharsets.UTF_8).replace("%3A", ":"))
                .queryParam("end", URLEncoder.encode(start, StandardCharsets.UTF_8).replace("%3A", ":"))
                .queryParam("uris", uris)
                .toUriString();
        return get(path);
    }
}
