package se.ju23.typespeeder.Spel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.ju23.typespeeder.Leaderboard.LeaderboardManager;
import se.ju23.typespeeder.database.User;

import java.util.Scanner;

@Component
public class GameManager {
    private final Scanner sc = new Scanner(System.in);
    private final LeaderboardManager leaderboardManager;
    private Challenge challenge;
    private User currentUser;

    @Autowired
    public GameManager(LeaderboardManager leaderboardManager) {
        this.leaderboardManager = leaderboardManager;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        this.challenge = new Challenge(leaderboardManager, currentUser, sc);
    }

    public void play() {
        System.out.println("Välj nivå: ");
        System.out.println("1. LEVEL1");
        System.out.println("2. LEVEL2");
        System.out.println("3. LEVEL3");
        int gameTypeChoice = sc.nextInt();
        sc.nextLine();

        try {
            Challenge.GameLevel level = Challenge.GameLevel.values()[gameTypeChoice - 1];
            challenge.startChallenge();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Ogiltigt val, försök igen.");
        }
    }
}
