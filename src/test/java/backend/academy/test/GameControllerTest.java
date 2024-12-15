package backend.academy.test;

import backend.academy.dictionary.WordDto;
import backend.academy.game.GameController;
import backend.academy.game.levels.Levels;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameControllerTest {

    @Mock
    private Scanner mockScanner;
    private GameController gameController;
    private WordDto wordDto;
    private Levels level;

    @BeforeEach
    void setUp() {
        mockScanner = mock(Scanner.class);

        wordDto = new WordDto(
            "дельтаплан",
            "Сверхлегкий безмоторный летательный аппарат, похожий на планер с треугольным крылом."
        );
        level = Levels.MEDIUM;
        gameController = new GameController(mockScanner, System.out);
    }

    @Test
    void start_winsGame(){
        when(mockScanner.nextLine()).thenReturn("д", "е", "л", "ь", "т", "а", "п", "н");

        gameController.start(wordDto, level);

        assertTrue(gameController.gameSession().isCurrentAnswerStateEqualToAnswer());
    }

    @Test
    void start_losesGame() {
        when(mockScanner.nextLine()).thenReturn("а", "б", "в", "г", "д", "е", "ж", "з");

        gameController.start(wordDto, level);

        assertFalse(gameController.gameSession().isCurrentAnswerStateEqualToAnswer());
    }

    @Test
    void start_usesHint_shortcut() {
        when(mockScanner.nextLine())
            .thenReturn("h")
            .thenReturn("д", "е", "л", "ь", "т", "а", "п", "н");


        gameController.start(wordDto, level);

        assertTrue(gameController.gameSession().isCurrentAnswerStateEqualToAnswer());
    }

    @Test
    void start_usesHint_longcut() {
        when(mockScanner.nextLine())
            .thenReturn("HELP")
            .thenReturn("д", "е", "л", "ь", "т", "а", "п", "н");


        gameController.start(wordDto, level);

        assertTrue(gameController.gameSession().isCurrentAnswerStateEqualToAnswer());
    }

    @Test
    void start_correctInputDifferentCase() {
        when(mockScanner.nextLine()).thenReturn("Д", "Е", "л", "Ь", "т", "А", "П", "Н");

        gameController.start(wordDto, level);

        assertTrue(gameController.gameSession().isCurrentAnswerStateEqualToAnswer());
    }

    @Test
    void start_incorrectInput() {
        when(mockScanner.nextLine()).thenReturn("h","д", "абвгд", "е", "л", "ь", "хелп" , "т", "а", "п", "н");

        gameController.start(wordDto, level);

        assertTrue(gameController.gameSession().isCurrentAnswerStateEqualToAnswer());
    }
}
