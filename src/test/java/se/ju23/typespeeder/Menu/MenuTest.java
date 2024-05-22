package se.ju23.typespeeder.Menu;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import se.ju23.typespeeder.Leaderboard.LeaderboardManager;

import se.ju23.typespeeder.Spel.GameManager;
import se.ju23.typespeeder.Spel.Patch;
import se.ju23.typespeeder.database.UserService;

import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class MenuTest {
    @Mock private UserService userService;
    @Mock private LoginManager loginManager;
    @Mock private GameManager gameManager;
    @Mock private LeaderboardManager leaderboardManager;
    @Mock private Patch patchNotesManager;
    @Mock private Scanner scanner;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        MockitoAnnotations.initMocks(this);
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testClassExists() {
        try {
            Class<?> clazz = Class.forName("se.ju23.typespeeder.Menu.Menu");
            Assertions.assertNotNull(clazz, "The class 'Menu' should exist.");
        } catch (ClassNotFoundException e) {
            Assertions.fail("The class 'Menu' does not exist.", e);
        }
    }

    @Test
    public void testMethodExists() {
        try {
            Class<?> clazz = Class.forName("se.ju23.typespeeder.Menu.Menu");
            Method method = clazz.getMethod("displayMenu");
            Assertions.assertNotNull(method, "The method 'displayMenu()' should exist in the class 'Menu'.");
        } catch (ClassNotFoundException e) {
            Assertions.fail("The class 'Menu' does not exist.", e);
        } catch (NoSuchMethodException e) {
            Assertions.fail("The method 'displayMenu()' does not exist in the class 'Menu'.", e);
        }
    }

    @Test
    public void testMenuImplementsInterface() {
        try {
            Class<?> menuClass = Class.forName("se.ju23.typespeeder.Menu.Menu");
            boolean implementsInterface = false;

            Class<?>[] interfaces = menuClass.getInterfaces();
            for (Class<?> iface : interfaces) {
                if (iface.equals(MenuService.class)) {
                    implementsInterface = true;
                    break;
                }
            }

            Assertions.assertTrue(implementsInterface, "The class 'Menu' should implement the interface 'MenuService'.");
        } catch (ClassNotFoundException e) {
            Assertions.fail("The class 'Menu' could not be found", e);
        }
    }

    @Test
    public void testDisplayMenuCallsGetMenuOptionsAndReturnsAtLeastFive() {
        when(scanner.nextInt()).thenReturn(1);
        when(scanner.nextLine()).thenReturn("some input", "");
        se.ju23.typespeeder.Menu.Menu menuMock = spy(new se.ju23.typespeeder.Menu.Menu(userService,loginManager,gameManager,leaderboardManager,patchNotesManager,scanner));
        menuMock.displayMenu();
        verify(menuMock, times(1)).getMenuOptions();
        Assertions.assertTrue(menuMock.getMenuOptions().size() >= 5, "'getMenuOptions()' should return at least 5 alternatives.");
    }

    @Test
    public void menuShouldHaveAtLeastFiveOptions() {
        se.ju23.typespeeder.Menu.Menu menu = new se.ju23.typespeeder.Menu.Menu(userService,loginManager,gameManager,leaderboardManager,patchNotesManager,scanner);
        List<String> options = menu.getMenuOptions();
        Assertions.assertTrue(options.size() >= 5, "The menu should contain at least 5 alternatives.");
    }

    @Test
    public void menuShouldPrintAtLeastFiveOptions() {
        when(scanner.nextInt()).thenReturn(1);
        when(scanner.nextLine()).thenReturn("some input", "");
        new se.ju23.typespeeder.Menu.Menu(userService,loginManager,gameManager,leaderboardManager,patchNotesManager,scanner).displayMenu();
        long count = outContent.toString().lines().count();
        Assertions.assertTrue(count >= 5, "The menu should print out at least 5 alternatives.");
    }

    @Test
    public void testUserCanChooseSwedishLanguage() {
        when(scanner.nextInt()).thenReturn(1);
        when(scanner.nextLine()).thenReturn("some input", "");
        String input = "svenska\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        se.ju23.typespeeder.Menu.Menu menu = new Menu(userService,loginManager,gameManager,leaderboardManager,patchNotesManager,scanner);
        menu.displayMenu();

        String consoleOutput = outContent.toString();
        Assertions.assertTrue(consoleOutput.contains("Välj språk (svenska/engelska):"), "Menu should prompt for language selection.");
        Assertions.assertTrue(consoleOutput.contains("Svenska valt."), "Menu should confirm Swedish language selection.");
    }

}