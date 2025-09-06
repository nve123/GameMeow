package ru.education.shop;

public class Price {
    private final int gold;
    private final int ore;
    private final int wood;

    public Price(int gold, int ore, int wood) {
        this.gold = gold;
        this.ore = ore;
        this.wood = wood;
    }

    public int getGold() {
        return gold;
    }

    public int getOre() {
        return ore;
    }

    public int getWood() {
        return wood;
    }
}
