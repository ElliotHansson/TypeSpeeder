package se.ju23.typespeeder.Spel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaderboardService {
    private final GameResultsRepository gameResultsRepository;

    @Autowired
    public LeaderboardService(GameResultsRepository gameResultsRepository) {

        this.gameResultsRepository = gameResultsRepository;
    }

    public List<GameResults> getLeaderboard() {
        return gameResultsRepository.findAllByOrderByScoreDesc();
    }
    public GameResults addGameResult(String username, int score) {
        GameResults newResult = new GameResults();
        newResult.setUsername(username);
        newResult.setScore(score);
        return gameResultsRepository.save(newResult);
    }
}
