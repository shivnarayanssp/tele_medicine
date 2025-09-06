package dev.shiv4u.telemedicine.repositories;

import dev.shiv4u.telemedicine.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface SessionRepository extends JpaRepository<Session, UUID> {
    Optional<Session> findBytokenAndUser_id(String token, UUID userId);
}
