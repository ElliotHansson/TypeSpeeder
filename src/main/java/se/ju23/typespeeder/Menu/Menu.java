package se.ju23.typespeeder.Menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.ju23.typespeeder.Leaderboard.LeaderboardManager;
import se.ju23.typespeeder.Spel.*;
import se.ju23.typespeeder.database.User;
import se.ju23.typespeeder.database.UserService;


import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@Component
public class Menu implements MenuService {
    private String currentLanguage;
    @Autowired
    private Scanner scanner;
    private final UserService userService;
    private final LoginManager loginManager;
    private final GameManager gameManager;
    private final LeaderboardManager leaderboardManager;
    private final PatchNotesManager patchNotesManager;
    private boolean isloggedIn = false;

    @Autowired
    public Menu(UserService userService, LoginManager loginManager, GameManager gameManager, LeaderboardManager leaderboardManager, PatchNotesManager patchNotesManager, Scanner scanner) {
        this.userService = userService;
        this.loginManager = loginManager;
        this.gameManager = gameManager;
        this.leaderboardManager = leaderboardManager;
        this.patchNotesManager = patchNotesManager;
        this.scanner = scanner;
        this.currentLanguage = "SVENSKA";
    }

    @Override
    public void promptForLanguage() {
        System.out.println("Välj språk (svenska/engelska):");
        String languageChoice = scanner.nextLine().trim().toLowerCase();

        if ("engelska".equals(languageChoice)) {
            currentLanguage = "ENGLISH";
            System.out.println("Engelska valt.");
        } else {

            currentLanguage = "SVENSKA";
            System.out.println("Svenska valt.");
        }

        showAuthenticationMenu();
    }
    @Override
    public void showAuthenticationMenu() {
        while (!isloggedIn) {
            System.out.println("\nVälkommen till TypeSpeeder!");
            System.out.println("1. Logga in");
            System.out.println("2. Skapa konto");
            System.out.println("3. Korrigera konto");
            System.out.println("Välj ett alternativ.");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    createAccount();
                    break;
                case 3:
                    correctAccount();
                    break;

                default:
                    System.out.println("Ogiltigt alternativ, försök igen.");
            }
        }
        displayMenu();
    }
    @Override
    public void login() {
        User user = loginManager.login();
        if (user != null) {
            gameManager.setCurrentUser(user);
            isloggedIn = true;
            System.out.println("Inloggningen lyckades!");
        } else {
            System.out.println("Inloggningen misslyckades");
        }
    }
    @Override
    public void createAccount() {
        System.out.println("Ange användarnamn");
        String username = scanner.nextLine();
        System.out.println("Ange lösenord:");
        String password = scanner.nextLine();
        System.out.println("Ange Spelnamn:");
        String inGameName = scanner.nextLine();

        boolean success = userService.createUser(username, password, inGameName);
        if (success) {
            System.out.println("Kontot skapat framgångrikt!");
            isloggedIn = true;
        } else {
            System.out.println("Kunde inte skapa konto användarnamnet kan vara upptaget.");
        }
    }
    @Override
    public void correctAccount() {
        System.out.println("Ange användarens ID");
        Long userId = Long.parseLong(scanner.nextLine());

        System.out.println("Ange nytt lösenord");
        String newPassword = scanner.nextLine();

        System.out.println("Ange nytt spelnamn");
        String newInGameName = scanner.nextLine();

        try {
            User updatedUser = userService.updateUser(userId, newPassword, newInGameName);
            System.out.println("Uppdaterat");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
    public List<String> getMenuOptions() {
        return Arrays.asList("Spela spelet", "Topplista", "Patch Notes", "Avsluta", "Logga ut");
    }
    @Override
    public void displayMenu() {
        List<String> options = getMenuOptions();
        System.out.println("\nHuvudmeny");
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }
        System.out.println("Välj ett alternativ.");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                gameManager.play();
                break;
            case 2:
                showLeaderboardsMenu();
                break;
            case 3:
                showPatchNotesMenu();
                break;
            case 4:
                System.out.println("Avslutar...");
                System.exit(0);
                break;
            case 5:
                System.out.println("Loggar ut...");
                isloggedIn = false;
                showAuthenticationMenu();
                break;
            default:
                System.out.println("Ogiltigt alternativ, försök igen.");
        }
    }
    private void showPatchNotesMenu() {
        System.out.println("\nPatch Anteckningar");
        patchNotesManager.showPatchNotes();
    }
    @Override
    public void showLeaderboardsMenu() {
        System.out.println("Välj Leaderboard");
        System.out.println("1. Level 1");
        System.out.println("2. Level 2");
        System.out.println("3. Level 3");
        System.out.println("4. Total poäng");
        int leaderboardChoice = scanner.nextInt();
        scanner.nextLine();

            switch (leaderboardChoice) {
                case 1:
                    leaderboardManager.showLeaderboardForGameType(GameType.LEVEL1);
                    break;
                case 2:
                    leaderboardManager.showLeaderboardForGameType(GameType.LEVEL2);
                    break;
                case 3:
                    leaderboardManager.showLeaderboardForGameType(GameType.LEVEL3);
                    break;
                case 4:
                    leaderboardManager.showTotalScoreLeaderboard();
                    break;
                default:
                    System.out.println("Försök igen.");
                    break;
            }
    }
}