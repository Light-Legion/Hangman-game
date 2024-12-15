package backend.academy.game;

import backend.academy.dictionary.WordDto;
import backend.academy.game.levels.Levels;
import backend.academy.utils.ANSI;
import backend.academy.utils.Message;
import java.io.PrintStream;
import java.util.Scanner;
import lombok.Getter;

public class GameController {

    private final Scanner in;
    private final PrintStream out;
    @Getter private GameSession gameSession;

    public GameController(Scanner in, PrintStream out) {
        this.in = in;
        this.out = out;
    }

    private static final String HELP_SHORTCUT = "h";
    private static final String HELP_LONGCUT = "help";

    public void start(WordDto wordDto, Levels level) {
        gameSession = new GameSession(wordDto, level.steps().size() - 1);
        boolean guessResult = false;

        Message.displayStep(0);
        Message.displayLives(gameSession.maxAttempts() - gameSession.attemptsMade());
        Message.displayCurrentState(gameSession.currentAnswerState().toString());
        Message.displayUsedLetters(gameSession.getUsedLetters());
        Message.displayHint("");

        while (!gameSession.isGameOver()) {

            boolean isInputCorrect = false;

            while (!isInputCorrect) {

                out.printf("%nВаш ввод: ");
                String guess = in.nextLine().trim().toLowerCase();

                if (HELP_SHORTCUT.equals(guess) || HELP_LONGCUT.equals(guess)) {
                    Message.displayHint(gameSession.getHint());
                } else if (guess.matches("[а-я]") && guess.length() == 1) {
                    if (gameSession.isUsedLetter(guess.charAt(0))) {
                        out.printf(ANSI.YELLOW + "%nЭта буква уже использовалась!%n" + ANSI.RESET);
                        continue;
                    }
                    guessResult = gameSession.makeGuess(guess.charAt(0));
                    isInputCorrect = true;
                } else {
                    out.printf(ANSI.RED + "%nНекорректный ввод! Попробуйте еще раз.%n" + ANSI.RESET);
                }

            }

            if (guessResult) {
                Message.success();
            } else {
                Message.failure();
            }

            Message.displayStep(level.steps().get(gameSession.attemptsMade()));
            Message.displayLives(gameSession.maxAttempts() - gameSession.attemptsMade());
            Message.displayCurrentState(gameSession.currentAnswerState().toString());
            Message.displayUsedLetters(gameSession.getUsedLetters());
            Message.displayHint("");
        }

        if (gameSession.isCurrentAnswerStateEqualToAnswer()) {
            Message.win();
        } else {
            Message.lose(gameSession.answer().word());
        }
    }

}
