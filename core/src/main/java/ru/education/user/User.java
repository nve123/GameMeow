package ru.education.user;

public class User {
    private static User instance;
    private int gold = 0;
    private int ore = 0;
    private int wood = 0;
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

}
