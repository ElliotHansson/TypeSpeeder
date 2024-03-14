package se.ju23.typespeeder.Menu;

import se.ju23.typespeeder.Spel.GameManager;
import se.ju23.typespeeder.Spel.LeaderboardManager;
import se.ju23.typespeeder.Spel.PatchNotesManager;

import java.util.Scanner;


public class MainMenu {
    private Scanner sc = new Scanner(System.in);
    private boolean isloggedIn = false;

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
        AccountManager accountManager = new AccountManager();
        isloggedIn = accountManager.createAccount();
        if (!isloggedIn) {
            System.out.println("Något gick fel försök igen.");
        }
    }
    private AccountCorrentionManager accountCorrentionManager= new AccountCorrentionManager();
    private GameManager gameManager = new GameManager();
    private LeaderboardManager leaderboardManager = new LeaderboardManager();
    private PatchNotesManager patchNotesManager = new PatchNotesManager();
    private void showMainMenu() {
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
                return;
            default:
                System.out.println("Ogiltigt alternativ, försök igen.");
        }
        isloggedIn = false;
    }
}