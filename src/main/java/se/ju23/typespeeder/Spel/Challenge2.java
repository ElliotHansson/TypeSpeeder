package se.ju23.typespeeder.Spel;

import se.ju23.typespeeder.database.User;
import se.ju23.typespeeder.Leaderboard.LeaderboardManager;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Challenge2 {
    private Random random = new Random();
    private List<String> textsSv = List.of(
            "Hej här kommer helikoptern",
            "Fulla fiskar simmar snett",
            "Träden är uppochned i chile"
    );
    private List<String> textsEn = List.of(
            "i like turtles",
            "Dinosaurs are like people",
            "Movies make me sick"
    );

    public void playLevel2(User currentUser, LeaderboardManager leaderboardManager, Scanner sc) {
        String language = chooseLanguage(sc);
        String originalText = getText(language);
        String mixedCaseText = mixCase(originalText);
        String originalCapitals = lettersToType(mixedCaseText);

        System.out.println("Texten här har slumpmässiga stora bokstäver, skriv dem i ordning:");
        System.out.println(mixedCaseText);

        System.out.println("Skriv här:");
        String userInput = sc.nextLine();

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

    private String chooseLanguage(Scanner sc) {
        System.out.println("Välj språk:");
        System.out.println("1. Svenska");
        System.out.println("2. Engelska");
        int choice = sc.nextInt();
        sc.nextLine();
        return choice == 1 ? "SV" : "EN";
    }

    private String getText(String language) {
        return language.equals("SV") ? textsSv.get(random.nextInt(textsSv.size())) : textsEn.get(random.nextInt(textsEn.size()));
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

    private String lettersToType(String text) {
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

    private int calculateScore(int numberOfCharacters) {
        return 100 * numberOfCharacters;
    }
}
