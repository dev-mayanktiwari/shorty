package tech.mayanktiwari.database.entities;

import java.time.Instant;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Entity
@Table(name = "click_counter", indexes = { @Index(columnList = "url_id") })
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClickCounter {
    @Id
    Integer id;

    @Builder.Default
    @Column(nullable = false)
    Integer clicks = 0;

    @Builder.Default
    @Column(name = "unique_clicks", nullable = false)
    Integer uniqueClicks = 0;

    @Column(name = "last_clicked_at")
    Instant lastClickedAt;

    @Builder.Default
    @Column(name = "created_at", nullable = false, updatable = false)
    Instant createdAt = Instant.now();

    // Owner side; MapsId makes id == url.id
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "url_id", foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    Url url;
}
