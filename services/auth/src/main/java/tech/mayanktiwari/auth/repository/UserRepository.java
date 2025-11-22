package tech.mayanktiwari.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tech.mayanktiwari.auth.models.Users;

public interface UserRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByUsername(String username);

    Optional<Users> findByEmail(String email);

    Optional<Users> findByEmailOrUsername(String email, String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
