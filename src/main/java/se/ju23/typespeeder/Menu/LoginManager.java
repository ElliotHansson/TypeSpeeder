package se.ju23.typespeeder.Menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.ju23.typespeeder.database.User;
import se.ju23.typespeeder.database.UserService;

import java.util.Scanner;

@Component
public class LoginManager {
    private final UserService userService;
    private final Scanner sc = new Scanner(System.in);

    @Autowired
    public LoginManager(UserService userService) {
    this.userService = userService;
    }

    public User login() {
        System.out.println("Ange användarnamn:");
        String username = sc.nextLine();
        System.out.println("Ange lösenord:");
        String password = sc.nextLine();

        User user = userService.authenticate(username, password);
        if (user != null) {
            System.out.println("Inloggningen lyckades!");
            return user;
        } else {
            System.out.println("Inloggning misslyckades.");
            return null;
        }
    }
}
