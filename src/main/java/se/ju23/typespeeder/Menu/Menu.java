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
    private final Patch patch;
    private boolean isLoggedIn = false;

    @Autowired
    public Menu(UserService userService, LoginManager loginManager, GameManager gameManager, LeaderboardManager leaderboardManager, Patch patch, Scanner scanner) {
        this.userService = userService;
        this.loginManager = loginManager;
        this.gameManager = gameManager;
        this.leaderboardManager = leaderboardManager;
        this.patch = patch;
        this.scanner = scanner;
        this.currentLanguage = "svenska";
    }

    @Override
    public void promptForLanguage() {
        System.out.println("Välj språk (svenska/engelska):");
        String languageChoice = scanner.nextLine().trim().toLowerCase();

        if ("engelska".equals(languageChoice)) {
            currentLanguage = "english";
            System.out.println("English chosen.");
        } else {
            currentLanguage = "svenska";
            System.out.println("Svenska valt.");
        }

        showAuthenticationMenu();
    }

    @Override
    public void showAuthenticationMenu() {
        if ("english".equals(currentLanguage)) {
            System.out.println("\nWelcome to TypeSpeeder!");
            System.out.println("1. Log in");
            System.out.println("2. Create account");
            System.out.println("3. Update account");
            System.out.println("Choose an option.");
        } else {
            System.out.println("\nVälkommen till TypeSpeeder!");
            System.out.println("1. Logga in");
            System.out.println("2. Skapa konto");
            System.out.println("3. Korrigera konto");
            System.out.println("Välj ett alternativ.");
        }
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
                if ("english".equals(currentLanguage)) {
                    System.out.println("Invalid option, please try again.");
                } else {
                    System.out.println("Ogiltigt alternativ, försök igen.");
                }
                break;
        }
    }

    @Override
    public void login() {
        User user = loginManager.login();
        if (user != null) {
            gameManager.setCurrentUser(user);
            isLoggedIn = true;
            if ("english".equals(currentLanguage)) {
                System.out.println("Login successful!");
            } else {
                System.out.println("Inloggningen lyckades!");
            }
            displayMenu();
        } else {
            if ("english".equals(currentLanguage)) {
                System.out.println("Login failed.");
            } else {
                System.out.println("Inloggningen misslyckades");
            }
        }
    }

    @Override
    public void createAccount() {
        if ("english".equals(currentLanguage)) {
            System.out.println("Enter username:");
            String username = scanner.nextLine();
            System.out.println("Enter password:");
            String password = scanner.nextLine();
            System.out.println("Enter game name:");
            String inGameName = scanner.nextLine();

            boolean success = userService.createUser(username, password, inGameName);
            if (success) {
                System.out.println("Account created successfully!");
                isLoggedIn = true;
            } else {
                System.out.println("Could not create account, username might be taken.");
            }
        } else {
            System.out.println("Ange användarnamn");
            String username = scanner.nextLine();
            System.out.println("Ange lösenord:");
            String password = scanner.nextLine();
            System.out.println("Ange Spelnamn:");
            String inGameName = scanner.nextLine();

            boolean success = userService.createUser(username, password, inGameName);
            if (success) {
                System.out.println("Kontot skapat framgångsrikt!");
                isLoggedIn = true;
                displayMenu();
            } else {
                System.out.println("Kunde inte skapa konto, användarnamnet kan vara upptaget.");
            }
        }
    }

    @Override
    public void correctAccount() {
        if ("english".equals(currentLanguage)) {
            System.out.println("Enter user ID:");
            Long userId = Long.parseLong(scanner.nextLine());

            System.out.println("Enter new password:");
            String newPassword = scanner.nextLine();

            System.out.println("Enter new game name:");
            String newInGameName = scanner.nextLine();

            try {
                User updatedUser = userService.updateUser(userId, newPassword, newInGameName);
                System.out.println("Updated.");
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        } else {
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
    }

    public List<String> getMenuOptions() {
        if ("english".equals(currentLanguage)) {
            return Arrays.asList("Play game", "Leaderboard", "Patch Notes", "Exit", "Log out");
        } else {
            return Arrays.asList("Spela spelet", "Topplista", "Patch Notes", "Avsluta", "Logga ut");
        }
    }

    @Override
    public void displayMenu() {
        List<String> options = getMenuOptions();
        if ("english".equals(currentLanguage)) {
            System.out.println("\nMain Menu");
        } else {
            System.out.println("\nHuvudmeny");
        }

        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }
        if ("english".equals(currentLanguage)) {
            System.out.println("Choose an option.");
        } else {
            System.out.println("Välj ett alternativ.");
        }

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
                if ("english".equals(currentLanguage)) {
                    System.out.println("Exiting...");
                } else {
                    System.out.println("Avslutar...");
                }
                System.exit(0);
                break;
            case 5:
                if ("english".equals(currentLanguage)) {
                    System.out.println("Logging out...");
                } else {
                    System.out.println("Loggar ut...");
                }
                isLoggedIn = false;
                showAuthenticationMenu();
                break;
            default:
                if ("english".equals(currentLanguage)) {
                    System.out.println("Invalid option, please try again.");
                } else {
                    System.out.println("Ogiltigt alternativ, försök igen.");
                }
                displayMenu();
                break;
        }
        promptForLanguage();
    }

    private void showPatchNotesMenu() {
        if ("english".equals(currentLanguage)) {
            System.out.println("\nPatch Notes");
        } else {
            System.out.println("\nPatch Anteckningar");
        }
        patch.showPatchNotes();
    }

    @Override
    public void showLeaderboardsMenu() {
        if ("english".equals(currentLanguage)) {
            System.out.println("Choose Leaderboard");
            System.out.println("1. Level 1");
            System.out.println("2. Level 2");
            System.out.println("3. Level 3");
            System.out.println("4. Total score");
        } else {
            System.out.println("Välj Leaderboard");
            System.out.println("1. Level 1");
            System.out.println("2. Level 2");
            System.out.println("3. Level 3");
            System.out.println("4. Total poäng");
        }
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
                if ("english".equals(currentLanguage)) {
                    System.out.println("Please try again.");
                } else {
                    System.out.println("Försök igen.");
                }
                break;
        }
    }
}
