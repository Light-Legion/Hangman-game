package backend.academy.game;

import backend.academy.dictionary.Category;
import backend.academy.dictionary.WordDto;
import backend.academy.game.levels.Levels;
import backend.academy.utils.ANSI;
import java.io.PrintStream;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Scanner;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Settings {

    final Scanner in;
    final PrintStream out;

    Levels level;
    Category category;
    @Setter WordDto wordDto;

    static final SecureRandom RANDOM = new SecureRandom();

    static final String UNCORRECT_PROMT = ANSI.RED + "%nНекорректное действие!"
        + " Попробуйте еще раз.%n" + ANSI.RESET;
    static final String CHOISE_PROMT = "Ваш выбор: ";

    public Settings(Scanner in, PrintStream out) {
        this.in = in;
        this.out = out;

        level = Levels.EASY;
        category = Category.values()[RANDOM.nextInt(Category.values().length)];
        wordDto = new WordDto("", "");
    }


    public void setDifficulty() {

        boolean set = true;
        while (set) {
            out.printf("%nВыберите уровень сложности игры:%n");
            out.println(ANSI.GREEN + "1 - " + Levels.EASY.level());
            out.println(ANSI.YELLOW + "2 - " + Levels.MEDIUM.level());
            out.println(ANSI.RED + "3 - " + Levels.HARD.level() + ANSI.RESET);
            out.print(CHOISE_PROMT);

            String select = in.nextLine().trim();
            switch (select) {
                case "1":
                case "2":
                case "3":
                    level = Levels.values()[Integer.parseInt(select) - 1];
                    set = false;
                    break;
                default:
                    out.printf(UNCORRECT_PROMT);
                    break;
            }
        }
    }

    public void setCategory() {

        boolean set = true;
        while (set) {
            out.printf("%nВыберите категорию слов:%n");
            out.print(ANSI.CYAN);
            out.println(ANSI.CYAN + "1 - Птицы");
            out.println("2 - Города России");
            out.println("3 - Одежда");
            out.println("4 - Рыбы");
            out.println("5 - Млекопитающие");
            out.println("6 - Профессии");
            out.println("7 - Спорт");
            out.println("8 - Транспорт");
            out.println(ANSI.MAGENTA + "0 - Случайная категория" + ANSI.RESET);
            out.print(CHOISE_PROMT);

            String select = in.nextLine().trim();
            switch (select) {
                case "1":
                case "2":
                case "3":
                case "4":
                case "5":
                case "6":
                case "7":
                case "8":
                    category = Category.values()[Integer.parseInt(select) - 1];
                    set = false;
                    break;
                case "0":
                    category = Category.values()[RANDOM.nextInt(Category.values().length)];
                    set = false;
                    break;
                default:
                    out.printf(UNCORRECT_PROMT);
                    break;
            }
        }
    }
}
