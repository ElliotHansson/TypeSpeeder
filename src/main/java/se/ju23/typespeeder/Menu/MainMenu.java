package se.ju23.typespeeder.Menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.ju23.typespeeder.Spel.*;
import se.ju23.typespeeder.database.User;
import se.ju23.typespeeder.database.UserService;

import java.util.Scanner;

@Component
public class MainMenu {
    private Scanner sc = new Scanner(System.in);
    private final UserService userService;
    private final LoginManager loginManager;
    private final GameManager gameManager;
    private final LeaderboardManager leaderboardManager;
    private final PatchNotesManager patchNotesManager;
    private boolean isloggedIn = false;

    @Autowired
    public MainMenu(UserService userService, LoginManager loginManager, GameManager gameManager, LeaderboardManager leaderboardManager, PatchNotesManager patchNotesManager) {
        this.userService = userService;
        this.loginManager = loginManager;
        this.gameManager = gameManager;
        this.leaderboardManager = leaderboardManager;
        this.patchNotesManager = patchNotesManager;
    }

    public void showAuthenticationMenu() {
        while (!isloggedIn) {
            System.out.println("\nVälkommen till TypeSpeeder!");
            System.out.println("1. Logga in");
            System.out.println("2. Skapa konto");
            System.out.println("3. Korrigera konto");
            System.out.println("Välj ett alternativ.");

            int choice = sc.nextInt();
            sc.nextLine();

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
        showMainMenu();
    }

    private void login () {
        isloggedIn = loginManager.login();
        if (!isloggedIn) {
            System.out.println("Fel uppgifter försök igen.");
        }
    }
    private void createAccount() {
        System.out.println("Ange användarnamn");
        String username = sc.nextLine();
        System.out.println("Ange lösenord:");
        String password = sc.nextLine();
        System.out.println("Ange Spelnamn:");
        String inGameName = sc.nextLine();

        boolean success = userService.createUser(username, password, inGameName);
        if (success) {
            System.out.println("Kontot skapat framgångrikt!");
            isloggedIn = true;
        } else {
            System.out.println("Kunde inte skapa konto användarnamnet kan vara upptaget.");
        }
    }
    private void correctAccount() {
        System.out.println("Ange användarens ID");
        Long userId = Long.parseLong(sc.nextLine());

        System.out.println("Ange nytt lösenord");
        String newPassword = sc.nextLine();

        System.out.println("Ange nytt spelnamn");
        String newInGameName = sc.nextLine();

        try {
            User updatedUser = userService.updateUser(userId, newPassword, newInGameName);
            System.out.println("Uppdaterat");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private void showMainMenu() {
        while (isloggedIn) {
            System.out.println("\nHuvudmeny");
            System.out.println("1. Spela spelet");
            System.out.println("2. Topplista");
            System.out.println("3. Patch Notes");
            System.out.println("4. Avsluta");
            System.out.println("Välj ett alternativ.");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    gameManager.play();
                    break;
                case 2:
                    leaderboardManager.showLeaderboard();
                    break;
                case 3:
                    patchNotesManager.showPatchNotes();
                    break;
                case 4:
                    System.out.println("Avslutar...");
                    isloggedIn = false;
                    break;
                default:
                    System.out.println("Ogiltigt alternativ, försök igen.");
            }
        }

    }
}