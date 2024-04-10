package se.ju23.typespeeder.Spel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.ju23.typespeeder.database.User;

import java.util.List;
import java.util.Random;
import java.util.Scanner;
@Component
public class GameManager {
    private final Scanner sc = new Scanner(System.in);
    private final LeaderboardManager leaderboardManager;
    private Random random =new Random();
    private User currentUser;

    @Autowired
    public  GameManager(LeaderboardManager leaderboardManager) {
        this.leaderboardManager = leaderboardManager;
    }
    public void setCurrentUser(User currentUser) {
        this.currentUser  = currentUser;
    }
    public void play() {
        do {
            String language = chooseLanguage();
            String textToType = getText(language);
            System.out.println("Du ska skriva följande: " + textToType);
            long startTime = System.currentTimeMillis();
            System.out.println("Skriv här: ");
            String userInput = sc.nextLine();
            long endTime = System.currentTimeMillis();

            boolean isCorrect = textToType.equalsIgnoreCase(userInput);
            long timeTaken = endTime - startTime;

            if (isCorrect) {
                System.out.println("Korrekt! Din tid: " + timeTaken + "ms");
                int score = calculateScore(timeTaken);
                currentUser.addScore(score);
                leaderboardManager.saveGameResult(currentUser, score);
                System.out.println("Du har tjänat" + score + "poäng.");
            } else {
                System.out.println("Felaktigt svar. Försökt igen.");

            }
            System.out.println("Vill du spela igen? (ja/nej)");
            String response = sc.nextLine().trim().toLowerCase();
            if ("nej".equals(response)) {
                break;
            }
        } while (true);
    }
    private String chooseLanguage() {
        System.out.println("Välj språk:");
        System.out.println("1. Svenska");
        System.out.println("2. Engelska");
        int choice = sc.nextInt();
        sc.nextLine();
        return choice == 1 ? "SV" : "EN";
    }
    private String getText(String language) {
        List<String> texts;
        if ("SV".equals(language)) {
            texts = List.of(
                    "Hej här kommer helikoptern",
                    "Fulla fiskar simmar snett",
                    "Träden är uppochned i chile"
            );
        } else {
            texts= List.of(
              "i like turtles",
              "Dinosaurs are like people",
              "Movies make me sick"
            );
        }
        Random random = new Random();
        return texts.get(random.nextInt(texts.size()));
    }
    private int calculateScore(long timeTaken) {
        int baseScore = 1000;
        int timePenalty = (int) timeTaken / 1000;
        return Math.max(baseScore - timePenalty, 0 );

    }
}
