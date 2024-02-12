package athat.ehubback.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import athat.ehubback.model.User;

public interface UserRepository extends JpaRepository<User, String>{
    User findByUsername(String username);
}