package se.ju23.typespeeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import se.ju23.typespeeder.Menu.Menu;

@SpringBootApplication
public class TypeSpeederApplication {

    @Autowired
    private Menu menu;

    public static void main(String[] args) {
        SpringApplication.run(TypeSpeederApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(Menu menu) {
        return args -> {
            menu.promptForLanguage();
        };
    }

}
