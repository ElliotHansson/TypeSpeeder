package se.ju23.typespeeder.Spel;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.ju23.typespeeder.database.User;
import java.util.List;

@Service
public class LeaderboardManager {
    private final GameResultsRepository gameResultsRepository;
    @Autowired
    public LeaderboardManager(GameResultsRepository gameResultsRepository) {
        this.gameResultsRepository = gameResultsRepository;
    }
    @Transactional
    public void saveGameResult(User user, int score) {
        GameResults newResult = new GameResults();
        newResult.setUsername(user.getUsername());
        newResult.setScore(score);
        gameResultsRepository.save(newResult);
    }

    public void showLeaderboard() {
        System.out.println("Leaderboard:");
        List<GameResults> results = gameResultsRepository.findAllByOrderByScoreDesc();
        for (GameResults result: results) {
            System.out.println(result.getUsername() + ": " + result.getScore());
        }
    }
}
