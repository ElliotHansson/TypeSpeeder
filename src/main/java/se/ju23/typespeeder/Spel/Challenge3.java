package se.ju23.typespeeder.Spel;

import se.ju23.typespeeder.Leaderboard.LeaderboardManager;
import se.ju23.typespeeder.database.User;

import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Challenge3 {

    private Random random = new Random();
    private Scanner sc = new Scanner(System.in);
    public void playLevel3(User currentUser, LeaderboardManager leaderboardManager, Scanner sc) {
        String text = "Hockey är nationalsporten i Kanada";
        String[] colors = {"\u001B[31m", "\u001B[32m", "\u001B[33m", "\u001B[34m", "\u001B[35m"};
        String greenColor = "\u001B[32m";
        String resetColor = "\u001B[0m";

        String colorizedText = colorizedText(text, colors, resetColor);
        System.out.println(colorizedText);
        System.out.println("Skriv in alla gröna bokstäver");

        long startTime = System.currentTimeMillis();
        String greenLetters = extractColorLetters(colorizedText, greenColor, resetColor);
        String userInput = sc.nextLine();
        long endTime = System.currentTimeMillis();

        boolean isCorrect = userInput.equalsIgnoreCase(greenLetters);
        long timeTaken = endTime - startTime;
        int score = calculateScore(userInput.length(), isCorrect, timeTaken);

        if (isCorrect) {
            System.out.println("Rätt!");
        } else {
            System.out.println("Fel, rätt svar var: " + greenLetters);
        }
        System.out.println("Du har tjänat " + score + " poäng.");
        leaderboardManager.saveGameResult(currentUser, score, timeTaken, GameType.LEVEL3);
    }

    private String colorizedText(String text, String[] colors, String resetColor) {
        StringBuilder builder = new StringBuilder();
        for (char c : text.toCharArray()) {
            String color = colors[random.nextInt(colors.length)];
            builder.append(color).append(c).append(resetColor);
        }
        return builder.toString();
    }

    private String extractColorLetters(String colorizedText, String greenColor, String resetColor) {
        StringBuilder builder = new StringBuilder();
        String[] parts = colorizedText.split(Pattern.quote(resetColor));
        for (String part : parts) {
            if (part.startsWith(greenColor)) {
                builder.append(part.substring(greenColor.length()));
            }
        }
        return builder.toString().replaceAll("[^A-Za]", "");
    }

    private int calculateScore(int length, boolean isCorrect, long timeTaken) {
        int baseScore = isCorrect ? 100 * length : 0;
        int timeBonus = (int) (1000 - timeTaken / 100);
        return Math.max(baseScore + timeBonus, 0);
    }
}
