package se.ju23.typespeeder.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.ju23.typespeeder.Spel.PatchNotesManager;

import java.util.Scanner;

@Configuration
public class AppConfig {

    @Bean
    public Scanner scanner() {
        return new Scanner(System.in);
    }
    @Bean
    public PatchNotesManager patchNotesManager() {
        return new PatchNotesManager();
    }
}
