package tech.mayanktiwari.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tech.mayanktiwari.auth.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmailOrUsername(String email, String username);

}
