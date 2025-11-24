package tech.mayanktiwari.url.models;

import java.time.Instant;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Builder
@Entity
@Table(name = "urls")
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String userId;

    @Column(unique = true, nullable = false)
    String shortUrl;

    @Column(nullable = false)
    String originalUrl;

    @Builder.Default
    @Column(nullable = false, updatable = false)
    Instant created = Instant.now();
}
