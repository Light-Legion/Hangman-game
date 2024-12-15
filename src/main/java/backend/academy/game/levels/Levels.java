package backend.academy.game.levels;

import java.util.List;
import java.util.stream.IntStream;
import lombok.Getter;

@Getter
public enum Levels {

    EASY("\uD83D\uDE0A Легкий", List.of(0, 1, 2, 3, 4, 5, 6, 7)),
    MEDIUM("\uD83D\uDE10 Средний", List.of(0, 2, 4, 5, 6, 7)),
    HARD("\uD83D\uDC80 Сложный", List.of(0, 2, 6, 7));

    private final String level;
    private final List<Integer> steps;

    Levels(String level, List<Integer> steps) {
        this.level = level;
        this.steps = steps;
    }
}
