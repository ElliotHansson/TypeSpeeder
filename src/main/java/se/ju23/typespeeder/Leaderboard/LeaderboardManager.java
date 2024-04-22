package se.ju23.typespeeder.Leaderboard;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.ju23.typespeeder.database.GameResults;
import se.ju23.typespeeder.Spel.GameResultsRepository;
import se.ju23.typespeeder.Spel.GameType;
import se.ju23.typespeeder.database.User;
import se.ju23.typespeeder.database.UserService;

import java.util.List;

@Service
public class LeaderboardManager {
    private final GameResultsRepository gameResultsRepository;
    private final UserService userService;

    @Autowired
    public LeaderboardManager(GameResultsRepository gameResultsRepository, UserService userService) {
        this.gameResultsRepository = gameResultsRepository;
        this.userService = userService;
    }

    @Transactional
    public void saveGameResult(User user, int score, long timeTaken, GameType gameType) {
        GameResults newResult = new GameResults();
        newResult.setPlayer(user);
        newResult.setUsername(user.getUsername());
        newResult.setScore(score);
        newResult.setResultTime(timeTaken);
        newResult.setGameType(gameType.ordinal());
        gameResultsRepository.save(newResult);
        userService.addScoreToUser(user, score);
    }

    public void showLeaderboardForGameType(GameType gameType) {
        System.out.println("Leaderboard for " + gameType + ":");
        List<GameResults> results = gameResultsRepository.findAllByGameTypeOrderByScoreDesc(gameType.ordinal());
        for (GameResults result : results) {
            System.out.println(result.getUsername() + ": " + result.getScore());
        }
    }

    public void showTotalScoreLeaderboard() {
        System.out.println("Total Score Leaderboard:");
        List<User> users = userService.findAllUsersOrderByTotalScoreDesc();
        for (User user : users) {
            System.out.println(user.getUsername() + ": " + user.getTotalScore());


        }
    }
}