package se.ju23.typespeeder.Spel;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameResultsRepository extends JpaRepository<GameResults, Long> {
    List<GameResults> findAllByOrderByScoreDesc();
}
