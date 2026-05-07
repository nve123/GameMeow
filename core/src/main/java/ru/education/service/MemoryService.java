package ru.education.service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Array;

import ru.education.user.User;

public class MemoryService {
    private static final Preferences preferences = Gdx.app.getPreferences("User saves");

    public static void saveUnlocks(Array<Boolean> lockedLvls) {
        boolean isUnlockLvl1 = lockedLvls.get(0);
        preferences.putBoolean("Look lvl 1", isUnlockLvl1);
        boolean isUnlockLvl2 = lockedLvls.get(1);
        preferences.putBoolean("Look lvl 2", isUnlockLvl2);
        boolean isUnlockLvl3 = lockedLvls.get(2);
        preferences.putBoolean("Look lvl 3", isUnlockLvl3);
        preferences.flush();
    }

    public static boolean loadUnlocks(Byte index) {
        switch (index) {
            case 0 -> {
                return preferences.getBoolean("Look lvl 1");
            }
            case 1 -> {
                return preferences.getBoolean("Look lvl 2");
            }
            case 2 -> {
                return preferences.getBoolean("Look lvl 3");
            }
        }

        return false;
    }

    public static Preferences getPreferences() {
        return preferences;
    }

    //    public static void saveUser() {
//        int hp = User.getInstance().getHp();
//        preferences.getInteger("Hp", hp);
//        preferences.flush();
//        int gold = User.getInstance().getGold();
//        preferences.getInteger("Gold", gold);
//        preferences.flush();
//        int ore = User.getInstance().getOre();
//        preferences.getInteger("Ore", ore);
//        preferences.flush();
//        int wood = User.getInstance().getWood();
//        preferences.getInteger("Wood", wood);
//        preferences.flush();
//    }
//
//    public static Integer loadUser(String type) {
//        switch (type) {
//            case "Hp" -> {
//                return preferences.getInteger("Hp");
//            }
//            case "Gold" -> {
//                return preferences.getInteger("Gold");
//            }
//            case "Ore" -> {
//                return preferences.getInteger("Ore");
//            }
//            case "Wood" -> {
//                return preferences.getInteger("Wood");
//            }
//        }
//        return null;
//    }
//
//    public static void saveIsAliveWave() {
//
//    }
//
//    public static void loadIsAliveWave() {
//
//    }
}

