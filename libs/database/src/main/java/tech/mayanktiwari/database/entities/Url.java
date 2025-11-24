package tech.mayanktiwari.database.entities;

import java.time.Instant;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Builder
@Entity
@Table(name = "urls", indexes = { @Index(columnList = "shortUrl"), @Index(columnList = "user_id") })
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    User user;

    @Column(unique = true, nullable = false)
    String shortUrl;

    @Column(nullable = false)
    String originalUrl;

    @Builder.Default
    @Column(nullable = false, updatable = false)
    Instant created = Instant.now();

    @OneToOne(mappedBy = "url", cascade = CascadeType.ALL, orphanRemoval = true)
    ClickCounter clickCounter;
}
