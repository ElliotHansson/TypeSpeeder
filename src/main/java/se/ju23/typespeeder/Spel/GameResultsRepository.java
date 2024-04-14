package se.ju23.typespeeder.Spel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.ju23.typespeeder.database.GameResults;

import java.util.List;

public interface GameResultsRepository extends JpaRepository<GameResults, Long> {

    List<GameResults> findAllByGameTypeOrderByScoreDesc(int gameType);

}
