package backend.academy.test;

import backend.academy.dictionary.Dictionary;
import backend.academy.dictionary.WordDto;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.*;

@Log4j2
class DictionaryTest {

    @Test
    void testTryToSetCategory_dictionaryIsEmpty() {
        Dictionary dictionary = new Dictionary(false);

        Throwable exception = assertThrows(IllegalStateException.class, () -> dictionary.tryToSetCategory("Птицы"));
        assertEquals("%nОшибка! Данные словаря не загружены.%n", exception.getMessage());
    }

    @Test
    void testTryToSetCategory_validCategory() {
        Dictionary dictionary = new Dictionary(true);

        dictionary.tryToSetCategory("Рыбы");

        assertEquals("Рыбы", dictionary.category());
    }

    @Test
    void testGetCategory_invalidCategory() {
        Dictionary dictionary = new Dictionary(true);

        dictionary.tryToSetCategory("Тестовая");

        assertNotNull(dictionary.category());
        assertNotEquals("Тестовая", dictionary.category());
    }

    @Test
    void testGetRandomWord_notSelectedCategory() {
        Dictionary dictionary = new Dictionary(true);

        dictionary.getRandomWord("");
        assertNotNull(dictionary.category());
    }

    @Test
    void testGetRandomWord_selectedCategory() {
        Dictionary dictionary = new Dictionary(true);

        dictionary.tryToSetCategory("Профессии");
        WordDto wordDto1 = dictionary.getRandomWord("машинист");
        WordDto wordDto2 = dictionary.getRandomWord("автомеханик");
        WordDto wordDto3 = dictionary.getRandomWord("тракторист");

        assertNotNull(wordDto1);
        assertNotEquals("машинист", wordDto1.word());

        assertNotNull(wordDto2);
        assertNotEquals("автомеханик", wordDto2.word());

        assertNotNull(wordDto3);
        assertNotEquals("тракторист", wordDto3.word());
    }

    @Test
    void testGetRandomWord_noSuchElementException() {
        Dictionary dictionary = new Dictionary(false);
        List<WordDto> words = new ArrayList<>();
        words.add(new WordDto("тест", "юнит-тест"));
        dictionary.dictionary().put("Тестовая категория", words);

        assertThrows(NoSuchElementException.class, () -> dictionary.getRandomWord("тест"));

        dictionary.dictionary().get("Тестовая категория").clear();

        assertThrows(NoSuchElementException.class, () -> dictionary.getRandomWord("тест"));
    }

    @Test
    void testGetRandomWord_illegalArgumentException() {
        Dictionary dictionary = new Dictionary(false);

        List<WordDto> words = new ArrayList<>();
        words.add(new WordDto("этоСловоБольшеЧемМаксимальнаяДлина",
            "Максимальная длина не больше 24"));
        words.add(new WordDto("о", "Очень маленькое слово"));
        dictionary.dictionary().put("Тестовая категория", words);

        log.info("Слова категории: {}", dictionary.dictionary().get("Тестовая категория").stream()
            .map(WordDto::word)
            .collect(Collectors.joining(", ")));

        assertThrows(IllegalArgumentException.class, () -> dictionary.getRandomWord("тест"));
        assertThrows(IllegalArgumentException.class, () -> dictionary.getRandomWord("этоСловоБольшеЧемМаксимальнаяДлина"));
        assertThrows(IllegalArgumentException.class, () -> dictionary.getRandomWord("о"));
    }
}
