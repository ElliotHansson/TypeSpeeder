package se.ju23.typespeeder.Menu;

import java.util.Scanner;

public class LoginManager {
    private Scanner sc;

    public LoginManager() {
        this.sc = new Scanner(System.in);
    }

    public boolean login() {
        System.out.println("Ange användarnamn");
        String username = sc.nextLine();

        System.out.println("Ange lösenord");
        String password = sc.nextLine();

        System.out.println("Inloggningen lyckades!");
        return true;
    }


}
