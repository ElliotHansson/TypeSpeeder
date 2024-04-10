package se.ju23.typespeeder;

import com.sun.tools.javac.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import se.ju23.typespeeder.Menu.MainMenu;

@SpringBootApplication
public class TypeSpeederApplication {

    @Autowired
    private MainMenu mainMenu;

    public static void main(String[] args) {
        SpringApplication.run(TypeSpeederApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(MainMenu mainMenu) {
        return args -> {
            mainMenu.showAuthenticationMenu();
        };
    }

}
