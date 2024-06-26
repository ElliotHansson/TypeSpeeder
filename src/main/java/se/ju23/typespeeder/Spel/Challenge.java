package se.ju23.typespeeder.Spel;

import se.ju23.typespeeder.Leaderboard.LeaderboardManager;
import se.ju23.typespeeder.database.User;

import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Challenge {
    private List<String> textsSwedish = List.of(
            "Hej här kommer helikoptern",
            "Fulla fiskar simmar snett",
            "Träden är uppochned i chile"
    );
    private List<String> textsEnglish = List.of(
            "I like turtles",
            "Dinosaurs are like people",
            "Movies make me sick"
    );
    private Random random = new Random();
    private Scanner scanner;
    private User currentUser;
    private LeaderboardManager leaderboardManager;

    public Challenge() {
        this.scanner = new Scanner(System.in);
    }

    public Challenge(LeaderboardManager leaderboardManager, User user, Scanner scanner) {
        this.leaderboardManager = leaderboardManager;
        this.currentUser = user;
        this.scanner = scanner;
    }

    public enum GameLevel {
        LEVEL1,
        LEVEL2,
        LEVEL3
    }

    public void startChallenge(GameLevel level, String language) {
        switch (level) {
            case LEVEL1:
                challenge1(language);
                break;
            case LEVEL2:
                challenge2(language);
                break;
            case LEVEL3:
                challenge3(language);
                break;
        }
    }

    private void challenge1(String language) {
        String textToType = getText(language, GameLevel.LEVEL1);
        System.out.println("Du ska skriva följande: " + textToType);

        long startTime = System.currentTimeMillis();
        System.out.println("Skriv här: ");

        String userInput = "";
        if (scanner.hasNextLine()) {
            userInput = scanner.nextLine();
        }

        long endTime = System.currentTimeMillis();

        boolean isCorrect = textToType.equalsIgnoreCase(userInput);
        long timeTaken = endTime - startTime;

        if (isCorrect) {
            System.out.println("Korrekt! Din tid: " + timeTaken + "ms");
            int score = calculateScore(timeTaken);
            leaderboardManager.saveGameResult(currentUser, score, timeTaken, GameType.LEVEL1);
            System.out.println("Du har tjänat " + score + " poäng.");
        } else {
            System.out.println("Felaktigt svar.");
        }
        System.out.println("Spelet är över. Programmet stängs av.");
        System.exit(0);
    }

    private void challenge2(String language) {
        String originalText = getText(language, GameLevel.LEVEL2);
        String mixedCaseText = mixCase(originalText);
        String originalCapitals = lettersToType(mixedCaseText);

        System.out.println("Texten här har slumpmässiga stora bokstäver, skriv dem i ordning:");
        System.out.println(mixedCaseText);

        System.out.println("Skriv här:");

        String userInput = "";
        if (scanner.hasNextLine()) {
            userInput = scanner.nextLine();
        }

        boolean isCorrect = playLevel(userInput, originalCapitals);
        if (isCorrect) {
            System.out.println("Korrekt!");
            int score = calculateScore(userInput.length());
            leaderboardManager.saveGameResult(currentUser, score, 0, GameType.LEVEL2);
            System.out.println("Du har tjänat " + score + " poäng.");
        } else {
            System.out.println("Felaktigt svar.");
        }
    }

    private void challenge3(String language) {
        String text = getText(language, GameLevel.LEVEL3);
        String[] colors = {"\u001B[31m", "\u001B[32m", "\u001B[33m", "\u001B[34m", "\u001B[35m"};
        String greenColor = "\u001B[32m";
        String resetColor = "\u001B[0m";

        String colorizedText = colorizedText(text, colors, resetColor);
        System.out.println(colorizedText);
        System.out.println("Skriv in alla gröna bokstäver");

        long startTime = System.currentTimeMillis();
        String greenLetters = extractColorLetters(colorizedText, greenColor, resetColor);

        String userInput = "";
        if (scanner.hasNextLine()) {
            userInput = scanner.nextLine();
        }

        long endTime = System.currentTimeMillis();

        boolean isCorrect = userInput.equalsIgnoreCase(greenLetters);
        long timeTaken = endTime - startTime;
        int score = calculateScore(userInput.length(), isCorrect, timeTaken);

        if (isCorrect) {
            System.out.println("Rätt!");
        } else {
            System.out.println("Fel, rätt svar var: " + greenLetters);
        }
        System.out.println("Du har tjänat " + score +  " poäng.");
        leaderboardManager.saveGameResult(currentUser, score, timeTaken, GameType.LEVEL3);
    }

    private String getText(String language, GameLevel level) {
        switch (level) {
            case LEVEL1:
                return language.equals("SV") ? textsSwedish.get(random.nextInt(textsSwedish.size())) : textsEnglish.get(random.nextInt(textsEnglish.size()));
            case LEVEL2:
                return language.equals("SV") ? textsSwedish.get(random.nextInt(textsSwedish.size())) : textsEnglish.get(random.nextInt(textsEnglish.size()));
            case LEVEL3:
                return language.equals("SV") ? textsSwedish.get(random.nextInt(textsSwedish.size())) : textsEnglish.get(random.nextInt(textsEnglish.size()));
            default:
                throw new IllegalArgumentException("Invalid game level");
        }
    }

    private String mixCase(String text) {
        StringBuilder mixedCaseText = new StringBuilder(text.toLowerCase());
        int lettersToCapitalize = random.nextInt(text.length() / 2) + 1;
        for (int i = 0; i < lettersToCapitalize; i++) {
            int charPosition;
            char currentChar;
            do {
                charPosition = random.nextInt(text.length());
                currentChar = mixedCaseText.charAt(charPosition);
            } while (Character.isUpperCase(currentChar));
            mixedCaseText.setCharAt(charPosition, Character.toUpperCase(currentChar));
        }
        return mixedCaseText.toString();
    }

    public void lettersToType() {
        String text = getText("SV", GameLevel.LEVEL1); // Get a sample text
        System.out.println("Bearbetar text för att hitta stora bokstäver: " + text);
        String capitals = lettersToType(text);
        System.out.println("Stora bokstäver i texten: " + capitals);
    }

    public String lettersToType(String text) {
        StringBuilder capitalLetters = new StringBuilder();
        for (char ch : text.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                capitalLetters.append(ch);
            }
        }
        return capitalLetters.toString();
    }

    private boolean playLevel(String input, String originalCapitals) {
        return input.equalsIgnoreCase(originalCapitals);
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
        return builder.toString().replaceAll("[^A-Za-z]", "");
    }

    private int calculateScore(long timeTaken) {
        int baseScore = 1000;
        int timePenalty = (int) timeTaken / 1000;
        return Math.max(baseScore - timePenalty, 0);
    }

    private int calculateScore(int length, boolean isCorrect, long timeTaken) {
        int baseScore = isCorrect ? 100 * length : 0;
        int timeBonus = (int) (1000 - timeTaken / 100);
        return Math.max(baseScore + timeBonus, 0);
    }
}
