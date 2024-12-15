package backend.academy.dictionary;

import lombok.Getter;

@Getter
public enum Category {
    BIRDS("birds.txt", "Птицы"),
    CITIES_OF_RUSSIA("cities_of_russia.txt", "Города России"),
    CLOTHES("clothes.txt", "Одежда"),
    FISHES("fishes.txt", "Рыбы"),
    MAMMALS("mammals.txt", "Млекопитающие"),
    PROFESSIONS("professions.txt", "Профессии"),
    SPORT("sport.txt", "Спорт"),
    TRANSPORT("transport.txt", "Транспорт"),;

    private final String fileName;
    private final String displayName;

    Category(String fileName, String displayName) {
        this.fileName = fileName;
        this.displayName = displayName;
    }
}
