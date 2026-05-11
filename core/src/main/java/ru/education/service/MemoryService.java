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
    public static void saveSoundSettings(boolean isOn) {
        preferences.putBoolean("isSoundOn", isOn);
        preferences.flush();
    }

    public static boolean loadIsSoundOn() {
        return preferences.getBoolean("isSoundOn", true);
    }

    public static void saveMusicSettings(boolean isOn) {
        preferences.putBoolean("isMusicOn", isOn);
        preferences.flush();
    }

    public static boolean loadIsMusicOn() {
        return preferences.getBoolean("isMusicOn", true);
    }
}

