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
    private final Challenge challenge = new Challenge();
    private final Challenge2 challenge2 = new Challenge2();
    private final Challenge3 challenge3 = new Challenge3();
    private User currentUser;

    @Autowired
    public GameManager(LeaderboardManager leaderboardManager) {
        this.leaderboardManager = leaderboardManager;
    }
    public void setCurrentUser(User currentUser) {
        this.currentUser  = currentUser;
    }
    public void play() {
        System.out.println("Välj nivå: ");
        System.out.println("1. LEVEL1");
        System.out.println("2. LEVEL2");
        System.out.println("3. LEVEL3");
        int gameTypeChoice = sc.nextInt();
        sc.nextLine();

        switch (gameTypeChoice) {
            case 1:
                challenge.startChallenge(currentUser, leaderboardManager,sc);
                break;
            case 2:
                challenge2.playLevel2(currentUser, leaderboardManager, sc);
                break;
            case 3:
                challenge3.playLevel3(currentUser, leaderboardManager, sc);
                break;
            default:
                System.out.println("Ogiltigt val, försök igen.");
                break;
        }
    }
}
