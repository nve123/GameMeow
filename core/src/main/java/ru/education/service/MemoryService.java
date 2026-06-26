package ru.education.service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Array;

import ru.education.user.User;

public class MemoryService {
    private static final Preferences preferences = Gdx.app.getPreferences("User saves");
    public static final String LOOK_LVL_1 = "Look lvl 1";
    public static final String LOOK_LVL_2 = "Look lvl 2";
    public static final String LOOK_LVL_3 = "Look lvl 3";
    public static final String LOOK_LVL_4 = "Look lvl 4";
    public static final String LOOK_LVL_5 = "Look lvl 5";
    public static final String IS_SOUND_ON = "isSoundOn";
    public static final String IS_MUSIC_ON = "isMusicOn";

    public static void saveUnlocks(Array<Boolean> lockedLvls) {
        boolean isUnlockLvl1 = lockedLvls.get(0);

        preferences.putBoolean(LOOK_LVL_1, isUnlockLvl1);
        boolean isUnlockLvl2 = lockedLvls.get(1);
        preferences.putBoolean(LOOK_LVL_2, isUnlockLvl2);
        boolean isUnlockLvl3 = lockedLvls.get(2);
        preferences.putBoolean(LOOK_LVL_3, isUnlockLvl3);
        boolean isUnlockLvl4 = lockedLvls.get(3);
        preferences.putBoolean(LOOK_LVL_4, isUnlockLvl4);
        boolean isUnlockLvl5 = lockedLvls.get(4);
        preferences.putBoolean(LOOK_LVL_5, isUnlockLvl5);
        preferences.flush();
    }

    public static boolean loadUnlocks(Byte index) {
        switch (index) {
            case 0 -> {
                return preferences.getBoolean(LOOK_LVL_1);
            }
            case 1 -> {
                return preferences.getBoolean(LOOK_LVL_2);
            }
            case 2 -> {
                return preferences.getBoolean(LOOK_LVL_3);
            }
            case 3 -> {
                return preferences.getBoolean(LOOK_LVL_4);

            }
            case 4 -> {
                return preferences.getBoolean(LOOK_LVL_5);
            }
        }

        return false;
    }

    public static Preferences getPreferences() {
        return preferences;
    }

    public static void saveSoundSettings(boolean isOn) {
        preferences.putBoolean(IS_SOUND_ON, isOn);
        preferences.flush();
    }

    public static boolean loadIsSoundOn() {
        return preferences.getBoolean(IS_SOUND_ON, true);
    }

    public static void saveMusicSettings(boolean isOn) {
        preferences.putBoolean(IS_MUSIC_ON, isOn);
        preferences.flush();
    }

    public static boolean loadIsMusicOn() {
        return preferences.getBoolean(IS_MUSIC_ON, true);
    }

    public static boolean isFirstStart() {
        return !MemoryService.getPreferences().contains(LOOK_LVL_2);
    }
}

