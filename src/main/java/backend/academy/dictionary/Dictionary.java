package backend.academy.dictionary;

import backend.academy.utils.ANSI;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Getter
@Log4j2
public class Dictionary {

    final HashMap<String, List<WordDto>> dictionary;
    String category;

    final SecureRandom secureRandom = new SecureRandom();
    static final int MIN_WORD_LENGTH = 2;
    static final int MAX_WORD_LENGTH = 24;

    public Dictionary(boolean loadDictionary) {
        dictionary = new HashMap<>();

        if (loadDictionary) {
            PrintStream out = System.out;
            for (Category cat : Category.values()) {
                try {
                    List<WordDto> wordDtos = readFile(cat.fileName());
                    dictionary.put(cat.displayName(), wordDtos);
                } catch (IOException ex) {
                    out.printf(ANSI.RED + ex.getMessage() + ANSI.RESET);
                }
            }
        }
    }

    private List<WordDto> readFile(String fileName) throws IOException {
        List<WordDto> list = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
            Objects.requireNonNull(
                getClass().getClassLoader().getResourceAsStream(
                    "categories_of_words/" + fileName),
                "Файл не найден: " + fileName), StandardCharsets.UTF_8))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String word = parts[0].trim().toLowerCase();
                    String hint = parts[1].trim();
                    list.add(new WordDto(word, hint));
                } else {
                    throw new IOException("%nНекорректный файл категории " + fileName + "%n");
                }
            }
        }

        return list;
    }

    public void setCategory(String category) {
        if (dictionary.isEmpty()) {
            throw new IllegalStateException("%nОшибка! Данные словаря не загружены.%n");
        }

        if (dictionary.containsKey(category)) {
            this.category = category;
        } else {
            this.category = dictionary.keySet().stream()
                .toList()
                .get(secureRandom.nextInt(dictionary.keySet().size()));
        }
    }

    public WordDto getWord(String word) {
        if (category == null) {
            tryToSetCategory("");
        }

        List<WordDto> filteredList = dictionary.get(category).stream()
            .filter(wordAndHint -> !wordAndHint.word().equalsIgnoreCase(word))
            .toList();

        if (filteredList.isEmpty()) {
            throw new NoSuchElementException("%nНедостаточно слов в словаре для данной категории.%n");
        }

        WordDto wordDto = filteredList.get(secureRandom.nextInt(filteredList.size()));

        if (wordDto.word().length() < MIN_WORD_LENGTH
            || wordDto.word().length() > MAX_WORD_LENGTH) {
            throw new IllegalArgumentException("%nНекорректная длина слова.%n");
        }

        return wordDto;
    }

}
