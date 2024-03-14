package se.ju23.typespeeder.Menu;

import java.util.Scanner;

public class AccountManager {
    private Scanner sc;

    public AccountManager() {
        this.sc = new Scanner(System.in);
    }
    public boolean createAccount() {
        System.out.println("Skapa konto: ");
        System.out.println("Ange användarnamn: ");
        String username = sc.nextLine();
        System.out.println("Ange Lösenord: ");
        String password = sc.nextLine();

        System.out.println("Du har nu skapat ett konto!");
        return true;
    }
}
