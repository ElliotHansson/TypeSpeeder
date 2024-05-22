package se.ju23.typespeeder.Spel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

public class ChallengeTest {
    @Test
    public void testChallengeClassExists() {
        try {
            Class<?> challengeClass = Class.forName("se.ju23.typespeeder.Spel.Challenge");
        } catch (ClassNotFoundException e) {
            Assertions.fail("Challenge class could not be found.");
        }
    }
    @Test
    public void testLettersToTypeMethodExists() {
        try {
            Class<?> challengeClass = Class.forName("se.ju23.typespeeder.Spel.Challenge");
            Method method = challengeClass.getMethod("lettersToType");
            Assertions.assertNotNull(method, "The method 'lettersToType' should exist in the Challenge class.");
        } catch (NoSuchMethodException | ClassNotFoundException e) {
            Assertions.fail("The method 'lettersToType' could not be found in the Challenge class.");
        }
    }
    @Test
    public void testStartChallengeMethodExists() {
        try {
            Class<?> challengeClass = Class.forName("se.ju23.typespeeder.Spel.Challenge");
            Method method = challengeClass.getMethod("startChallenge", Challenge.GameLevel.class, String.class);
            Assertions.assertNotNull(method, "The method 'startChallenge' should exist in the Challenge class.");
        } catch (NoSuchMethodException | ClassNotFoundException e) {
            Assertions.fail("The method 'startChallenge' could not be found in the Challenge class.");
        }
    }
}
