package se.ju23.typespeeder.Spel;

public enum GameType {
    LEVEL1(1),
    LEVEL2(2),
    LEVEL3(3);
    private final int value;
    private GameType(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
