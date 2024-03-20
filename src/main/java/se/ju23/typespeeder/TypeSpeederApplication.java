package se.ju23.typespeeder;

import com.sun.tools.javac.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import se.ju23.typespeeder.Menu.MainMenu;

@SpringBootApplication
public class TypeSpeederApplication implements CommandLineRunner {

    @Autowired
    private MainMenu mainMenu;

    public static void main(String[] args) {
        SpringApplication.run(TypeSpeederApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        mainMenu.showAuthenticationMenu();

    }

}
