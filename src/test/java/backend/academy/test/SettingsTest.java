package backend.academy.test;

import backend.academy.dictionary.Category;
import backend.academy.game.Settings;
import backend.academy.game.levels.Levels;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import java.util.Arrays;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SettingsTest {

    private Settings settings;
    @Mock
    private Scanner mockScanner;

    @BeforeEach
    void setUp() {
        mockScanner = mock(Scanner.class);
        settings = new Settings(mockScanner, System.out);
    }

    @Test
    void setDifficulty_setsLevelCorrectly() {
        when(mockScanner.nextLine())
            .thenReturn("2")
            .thenReturn("1")
            .thenReturn("3");

        settings.setDifficulty();
        assertEquals(Levels.MEDIUM, settings.level());

        settings.setDifficulty();
        assertEquals(Levels.EASY, settings.level());

        settings.setDifficulty();
        assertEquals(Levels.HARD, settings.level());
    }

    @Test
    void setDifficulty_handlesIncorrectInput() {
        when(mockScanner.nextLine())
            .thenReturn("test")
            .thenReturn("2");
        settings.setDifficulty();
        assertEquals(Levels.MEDIUM, settings.level());
    }

    @Test
    void setCategory_setsCategoryCorrectly() {
        when(mockScanner.nextLine())
            .thenReturn("4")
            .thenReturn("1")
            .thenReturn("2")
            .thenReturn("0");

        settings.setCategory();
        assertEquals(Category.FISHES, settings.category());

        settings.setCategory();
        assertEquals(Category.BIRDS, settings.category());

        settings.setCategory();
        assertEquals(Category.CITIES_OF_RUSSIA, settings.category());

        settings.setCategory();
        assertTrue(Arrays.asList(Category.values()).contains(settings.category()),
            "Данной категории нет в списке доступных категорий");
    }

    @Test
    void setCategory_handlesIncorrectInput() {
        when(mockScanner.nextLine())
            .thenReturn("test")
            .thenReturn("4");
        settings.setCategory();
        assertEquals(Category.FISHES, settings.category());
    }
}
