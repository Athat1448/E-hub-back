package athat.ehubback.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import athat.ehubback.model.User;

public interface UserRepository extends JpaRepository<User, UUID>{
    User findByUsername(String username);
}