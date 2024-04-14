package se.ju23.typespeeder.Menu;

import org.mockito.Mock;
import org.testng.annotations.Test;
import se.ju23.typespeeder.Leaderboard.LeaderboardManager;
import se.ju23.typespeeder.Spel.GameManager;
import se.ju23.typespeeder.Spel.PatchNotesManager;
import se.ju23.typespeeder.database.UserService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class MenuPerformanceTest {
    @Mock
    private UserService userService;
    @Mock private LoginManager loginManager;
    @Mock private GameManager gameManager;
    @Mock private LeaderboardManager leaderboardManager;
    @Mock private PatchNotesManager patchNotesManager;
    @Mock private Scanner scanner;

    private static final int MAX_EXECUTION_TIME_MENU = 1;
    private static final int MAX_EXECUTION_TIME_LANGUAGE_SELECTION = 100;
    private static final int MILLISECONDS_CONVERSION = 1_000_000;

    @Test
    public void testGetMenuOptionsExecutionTime() {
        long startTime = System.nanoTime();
        Menu menu = new Menu(userService,loginManager,gameManager,leaderboardManager,patchNotesManager,scanner);
        menu.getMenuOptions();
        long endTime = System.nanoTime();

        long duration = (endTime - startTime) / MILLISECONDS_CONVERSION;

        assertTrue(duration <= MAX_EXECUTION_TIME_MENU, "Menu display took too long. Execution time: " + duration + " ms.");
    }

    @Test
    public void testUserCanChooseSwedishLanguageAndPerformance() {
        when(scanner.nextInt()).thenReturn(1);
        when(scanner.nextLine()).thenReturn("some input", "");
        String input = "svenska\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        long startTime = System.nanoTime();

        Menu menu = new Menu(userService,loginManager,gameManager,leaderboardManager,patchNotesManager,scanner);
        menu.displayMenu();

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / MILLISECONDS_CONVERSION;

        String consoleOutput = outContent.toString();

        assertTrue(consoleOutput.contains("Välj språk (svenska/engelska):"), "Menu should prompt for language selection.");

        assertTrue(consoleOutput.contains("Svenska valt."), "Menu should confirm Swedish language selection.");


        assertTrue(duration <= MAX_EXECUTION_TIME_LANGUAGE_SELECTION, "Menu display and language selection took too long. Execution time: " + duration + " ms.");
    }

}

