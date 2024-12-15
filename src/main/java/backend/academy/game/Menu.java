package backend.academy.game;

import backend.academy.dictionary.Dictionary;
import backend.academy.utils.ANSI;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Menu {

    final Scanner in;
    final PrintStream out;

    final Settings settings;
    final Dictionary dictionary;

    static final String YES = "y";
    static final String SELECT_PROMT = "%nВыберите номер действия:%n";
    static final String CHOICE_PROMT = "Ваш выбор: ";
    static final String UNCORRECT_PROMT = ANSI.RED + "%nНекорректное действие! "
        + "Попробуйте еще раз.%n" + ANSI.RESET;
    static final String ERROR_PROMT = ANSI.RED + "Дальнейшее продолжение игры невозможно!"
        + "%nИгра будет завершена автоматически!%n" + ANSI.RESET;

    public Menu() {
        this.in = new Scanner(System.in, StandardCharsets.UTF_8);
        this.out = System.out;

        settings = new Settings(in, out);
        dictionary = new Dictionary(true);
    }

    public void run() {
        out.println(ANSI.BLUE + "-----ВИСЕЛИЦА-----" + ANSI.RESET);

        boolean play = true;
        while (play) {
            out.printf(SELECT_PROMT);
            out.println(ANSI.GREEN + "1 - Запуск игры");
            out.println(ANSI.YELLOW + "2 - Выбор уровня сложности и категории слов");
            out.println(ANSI.RED + "3 - Выход" + ANSI.RESET);
            out.print(CHOICE_PROMT);

            String action = in.nextLine().trim();

            switch (action) {
                case "1":
                    try {
                        playGame();
                    } catch (RuntimeException ex) {
                        out.printf(ex.getMessage());
                        out.printf(ERROR_PROMT);
                        play = false;
                    }
                    break;
                case "2":
                    try {
                        settingsGame();
                    } catch (IllegalStateException ex) {
                        out.println(ex.getMessage());
                        out.printf(ERROR_PROMT);
                        play = false;
                    }
                    break;
                case "3":
                    play = false;
                    break;
                default:
                    out.printf(UNCORRECT_PROMT);
            }
        }

        out.printf(ANSI.BLUE + "%n-----ИГРА ОКОНЧЕНА-----%n" + ANSI.RESET);
    }

    private void settingsGame() {

        out.printf(ANSI.BLUE + "%n-----Настройки игры-----%n" + ANSI.RESET);

        while (true) {
            out.printf(SELECT_PROMT);
            out.println(ANSI.GREEN + "1 - Уровень сложности.");
            out.println("2 - Категория слов.");
            out.println(ANSI.RED + "0 - Вернуться назад" + ANSI.RESET);
            out.print(CHOICE_PROMT);

            String action = in.nextLine().trim();

            switch (action) {
                case "1":
                    settings.setDifficulty();
                    break;
                case "2":
                    settings.setCategory();
                    break;
                case "0":
                    return;
                default:
                    out.printf(UNCORRECT_PROMT);
                    break;
            }
        }
    }

    private void playGame() {
        dictionary.tryToSetCategory(settings.category().displayName());
        GameController gameController = new GameController(in, out);

        do {
            try {
                settings.wordDto(dictionary.getRandomWord(settings.wordDto().word()));
            } catch (NoSuchElementException ex) {
                out.printf(ANSI.RED + ex.getMessage() + ANSI.RESET);
                out.printf("%nДля продолжения игры вам необходимо сменить категорию слов!%n");
                break;
            } catch (IllegalArgumentException ex) {
                out.printf(ANSI.RED + ex.getMessage() + ANSI.RESET);
                out.printf("Попробуйте запустить игру ещё раз!%n");
                break;
            } catch (IllegalStateException ex) {
                throw new RuntimeException(ex.getMessage(), ex);
            }

            out.printf(ANSI.BLUE + "%n-----Игра началась!-----%n" + ANSI.RESET);

            out.printf("%nУровень сложности: %s%nКатегория слов: %s%n",
                ANSI.MAGENTA + settings.level().level() + ANSI.RESET,
                ANSI.CYAN + settings.category().displayName() + ANSI.RESET
            );

            gameController.start(settings.wordDto(), settings.level());

            out.printf("%nСыграть снова или вернуться в главное меню? (Y / Any input)%n");
            out.print(CHOICE_PROMT);
        } while (YES.equalsIgnoreCase(in.nextLine().trim()));

    }
}
