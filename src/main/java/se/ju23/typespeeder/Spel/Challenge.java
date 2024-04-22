package se.ju23.typespeeder.Spel;

import se.ju23.typespeeder.Leaderboard.LeaderboardManager;
import se.ju23.typespeeder.database.User;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Challenge {
    private List<String> textsSwedish = List.of(
            "Hej här kommer helikoptern",
            "Fulla fiskar simmar snett",
            "Träden är uppochned i chile"
    );
    private List<String> textsEnglish = List.of(
            "i like turtles",
            "Dinosaurs are like people",
            "Movies make me sick"
    );
    private Random random = new Random();


    public void startChallenge() {

        User defaultUser = new User();
        LeaderboardManager defaultLeaderboardManager = new LeaderboardManager();
        Scanner defaultScanner = new Scanner(System.in);
        startChallenge(defaultUser, defaultLeaderboardManager, defaultScanner);
    }

    public void startChallenge(User currentUser, LeaderboardManager leaderboardManager, Scanner sc) {
        String language = chooseLanguage(sc);
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
            leaderboardManager.saveGameResult(currentUser, score, timeTaken, GameType.LEVEL1);
            System.out.println("Du har tjänat " + score + " poäng.");
        } else {
            System.out.println("Felaktigt svar. Försökt igen.");
        }
        System.out.println("Vill du spela igen? (ja/nej)");
        if ("nej".equals(sc.nextLine().trim().toLowerCase())) {
            return;
        }
    }

    private String chooseLanguage(Scanner sc) {
        System.out.println("Välj språk:");
        System.out.println("1. Svenska");
        System.out.println("2. Engelska");
        int choice = sc.nextInt();
        sc.nextLine();
        return choice == 1 ? "SV" : "EN";
    }

    private String getText(String language) {
        if ("SV".equals(language)) {
            return textsSwedish.get(random.nextInt(textsSwedish.size()));
        } else {
            return textsEnglish.get(random.nextInt(textsEnglish.size()));
        }
    }

    private int calculateScore(long timeTaken) {
        int baseScore = 1000;
        int timePenalty = (int) timeTaken / 1000;
        return Math.max(baseScore - timePenalty, 0);
    }
}
