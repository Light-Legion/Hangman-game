package backend.academy.game;

import backend.academy.dictionary.WordDto;
import java.util.HashSet;
import lombok.Getter;

@Getter
public class GameSession {

    final WordDto answer;
    final int maxAttempts;
    final StringBuilder currentAnswerState;
    int attemptsMade;

    final HashSet<Character> usedLetters;

    public GameSession(WordDto answer, int maxAttempts) {
        this.answer = answer;
        this.maxAttempts = maxAttempts;
        this.currentAnswerState = new StringBuilder("_".repeat(answer.word().length()));
        this.attemptsMade = 0;
        this.usedLetters = new HashSet<>();
    }

    public String getHint() {
        return answer.hint();
    }

    public String getUsedLetters() {
        return usedLetters.toString();
    }

    public boolean isUsedLetter(char letter) {
        return usedLetters.contains(letter);
    }

    public boolean isGameOver() {
        return attemptsMade >= maxAttempts || isCurrentAnswerStateEqualToAnswer();
    }

    public boolean isCurrentAnswerStateEqualToAnswer() {
        return currentAnswerState.toString().equals(answer.word());
    }

    public boolean makeGuess(char guess) {

        boolean isCorrect = false;

        usedLetters.add(guess);

        for (int i = 0; i < answer.word().length(); i++) {
            if (answer.word().charAt(i) == guess) {
                currentAnswerState.setCharAt(i, guess);
                isCorrect = true;
            }
        }

        if (!isCorrect) {
            attemptsMade++;
        }

        return isCorrect;

    }
}
