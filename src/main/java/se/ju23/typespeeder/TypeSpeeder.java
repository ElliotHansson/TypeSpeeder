package se.ju23.typespeeder;

import se.ju23.typespeeder.Menu.MainMenu;
import se.ju23.typespeeder.database.UserService;

public class TypeSpeeder {
    public static void main (String[] args) {
        MainMenu mainMenu = new MainMenu();
        mainMenu.showAuthenticationMenu();
    }
}
