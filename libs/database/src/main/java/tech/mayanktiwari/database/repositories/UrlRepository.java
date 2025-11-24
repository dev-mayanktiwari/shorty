package tech.mayanktiwari.database.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tech.mayanktiwari.database.entities.Url;

@Repository
public interface UrlRepository extends JpaRepository<Url, Integer> {

    Optional<Url> findByShortUrl(String shortUrl);

}
