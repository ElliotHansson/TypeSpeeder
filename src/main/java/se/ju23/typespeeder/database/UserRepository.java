package se.ju23.typespeeder.database;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername (String username);
    List<User> findAllByOrderByTotalScoreDesc();
}
