package se.ju23.typespeeder.Menu;

import org.springframework.stereotype.Component;
import se.ju23.typespeeder.Spel.GameManager;
import se.ju23.typespeeder.Spel.LeaderboardManager;
import se.ju23.typespeeder.Spel.PatchNotesManager;
import se.ju23.typespeeder.database.UserService;

import java.util.Scanner;

@Component
public class MainMenu {
    private Scanner sc = new Scanner(System.in);
    private final UserService userService;
    private boolean isloggedIn = false;

    public MainMenu(UserService userService) {
        this.userService = userService;
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
                    accountCorrentionManager.correctAccount();
                    break;

                default:
                    System.out.println("Ogiltigt alternativ, försök igen.");
            }
        }
        showMainMenu();
    }

    private void login () {
        LoginManager loginManager= new LoginManager();
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
    private AccountCorrentionManager accountCorrentionManager= new AccountCorrentionManager();
    private GameManager gameManager = new GameManager();
    private LeaderboardManager leaderboardManager = new LeaderboardManager();
    private PatchNotesManager patchNotesManager = new PatchNotesManager();
    private void showMainMenu() {
        while (isloggedIn) {
            System.out.println("4. Spela spelet");
            System.out.println("5. Topplista");
            System.out.println("6. Patch Notes");
            System.out.println("7. Avsluta");
            System.out.println("Välj ett alternativ.");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 4:
                    gameManager.play();
                    break;
                case 5:
                    leaderboardManager.showLeaderboard();
                    break;
                case 6:
                    patchNotesManager.showPatchNotes();
                    break;
                case 7:
                    System.out.println("Avslutar...");
                    isloggedIn = false;
                    break;
                default:
                    System.out.println("Ogiltigt alternativ, försök igen.");
            }
        }

    }
}