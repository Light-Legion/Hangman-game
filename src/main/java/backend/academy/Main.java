package backend.academy;

import backend.academy.game.Menu;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.run();
    }
}
