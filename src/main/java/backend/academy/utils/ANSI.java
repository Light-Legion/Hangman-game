package backend.academy.utils;

public enum ANSI {
    RESET("\u001B[0m"),
    RED("\u001B[31;1m"),
    GREEN("\u001B[32;1m"),
    YELLOW("\u001B[33;1m"),
    BLUE("\u001B[34;1m"),
    MAGENTA("\u001B[35;1m"),
    CYAN("\u001B[36;1m"),
    WHITE("\u001B[37;1m");

    private final String code;

    ANSI(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}
