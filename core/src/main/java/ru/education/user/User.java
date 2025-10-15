package ru.education.user;

import ru.education.shop.Item;
import ru.education.shop.Price;

public class User {
    private static User instance;
    private int gold = 100;
    private int ore = 200;
    private int wood = 150;
    private int hp = 100;

    private User() {
    }

    public static User getInstance() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }

    public String fullInfo() {
        return "Hp : " + hp + "\n" +
            "Gold : " + gold + "\n" +
            "Ore : " + ore + "\n" +
            "Wood : " + wood + "\n";
    }

    public void getDmg(int dmg) {
        User.getInstance().hp -= dmg;
    }

    public void incOre(int ore) {
        User.getInstance().ore += ore;
    }

    public void incWood(int wood) {
        User.getInstance().wood += wood;
    }

    public void incGold(int gold) {
        User.getInstance().gold += gold;
    }

    public void buyItem(Item curChoice) {
        Price price = curChoice.getPrice();
        gold -= price.getGold();
        wood -= price.getWood();
        ore -= price.getOre();
    }

    public boolean canBuy(Price price) {
        return price.getWood() <= wood && price.getOre() <= ore && price.getGold() <= gold;
    }

}
