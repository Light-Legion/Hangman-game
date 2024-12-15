package backend.academy.test;

import backend.academy.dictionary.WordDto;
import backend.academy.game.GameSession;
import backend.academy.game.levels.Levels;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameSessionTest {

    private GameSession gameSession;

    @BeforeEach
    void setUp() {
        gameSession = new GameSession(
            new WordDto(
                "калининград",
                "Город, который в прошлом знали как Кенигсберг."
            ),
            Levels.MEDIUM.steps().size() - 1
        );
    }

    @Test
    void getHint_returnsCorrectHint() {
        assertEquals("Город, который в прошлом знали как Кенигсберг.", gameSession.getHint());
        assertNotEquals("Город, в котором находится самая длинная набережная в России.", gameSession.getHint());
    }

    @Test
    void getUsedLetters_returnsCorrectValues() {
        assertEquals("[]", gameSession.getUsedLetters());
        gameSession.makeGuess('а');
        assertEquals("[а]", gameSession.getUsedLetters());
        gameSession.makeGuess('б');
        gameSession.makeGuess('я');
        gameSession.makeGuess('н');
        gameSession.makeGuess('г');
        assertEquals("[а, б, г, н, я]", gameSession.getUsedLetters());
    }

    @Test
    void isUsedLetter_checksIfLetterUsed() {
        assertFalse(gameSession.isUsedLetter('а'));
        gameSession.makeGuess('а');
        assertTrue(gameSession.isUsedLetter('а'));
    }

    @Test
    void isGameOver_returnsWhenWin() {
        assertFalse(gameSession.isGameOver());
        gameSession.makeGuess('к');
        gameSession.makeGuess('а');
        gameSession.makeGuess('л');
        gameSession.makeGuess('и');
        gameSession.makeGuess('н');
        gameSession.makeGuess('г');
        gameSession.makeGuess('р');
        assertFalse(gameSession.isGameOver());
        gameSession.makeGuess('д');
        assertTrue(gameSession.isGameOver());
    }

    @Test
    void isGameOver_returnsWhenLose() {
        assertFalse(gameSession.isGameOver());
        gameSession.makeGuess('к');
        gameSession.makeGuess('й');
        gameSession.makeGuess('ц');
        gameSession.makeGuess('у');
        gameSession.makeGuess('е');
        gameSession.makeGuess('ш');
        assertTrue(gameSession.isGameOver());
    }

    @Test
    void isCurrentAnswerStateEqualToAnswer_matchesAnswer() {
        assertFalse(gameSession.isCurrentAnswerStateEqualToAnswer());
        gameSession.makeGuess('к');
        gameSession.makeGuess('а');
        gameSession.makeGuess('л');
        gameSession.makeGuess('и');
        gameSession.makeGuess('н');
        gameSession.makeGuess('г');
        gameSession.makeGuess('р');
        assertFalse(gameSession.isCurrentAnswerStateEqualToAnswer());
        gameSession.makeGuess('д');
        assertTrue(gameSession.isCurrentAnswerStateEqualToAnswer());
    }

    @Test
    void makeGuess_returnsCorrectResult() {
        assertTrue(gameSession.makeGuess('а'));
        assertFalse(gameSession.makeGuess('б'));
        assertFalse(gameSession.makeGuess('z'));
    }

    @Test
    void attemptsMade_returnsCorrectCount() {
        assertEquals(0, gameSession.attemptsMade());
        gameSession.makeGuess('а');
        assertEquals(0, gameSession.attemptsMade());
        gameSession.makeGuess('б');
        assertEquals(1, gameSession.attemptsMade());
        gameSession.makeGuess('й');
        gameSession.makeGuess('ц');
        assertEquals(3, gameSession.attemptsMade());
    }

    @Test
    void currentAnswerState_returnsCorrectState() {
        assertEquals("___________", gameSession.currentAnswerState().toString());
        gameSession.makeGuess('а');
        assertEquals("_а_______а_", gameSession.currentAnswerState().toString());
        gameSession.makeGuess('б');
        gameSession.makeGuess('в');
        assertEquals("_а_______а_", gameSession.currentAnswerState().toString());
        gameSession.makeGuess('и');
        gameSession.makeGuess('к');
        assertEquals("ка_и_и___а_", gameSession.currentAnswerState().toString());
    }
}
