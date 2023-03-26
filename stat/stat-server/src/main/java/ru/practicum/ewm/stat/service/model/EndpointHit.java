package ru.practicum.ewm.stat.service.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Builder
@Getter
@Setter
@Entity
@Table(name = "endpoint_hits")
@AllArgsConstructor
@NoArgsConstructor
public class EndpointHit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String app;

    @Column(nullable = false)
    private String uri;

    @Column(nullable = false)
    private String ip;

    @Column(nullable = false, name = "hit_time")
    private Date timestamp;

}
