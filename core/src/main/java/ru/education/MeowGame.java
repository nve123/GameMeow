package ru.education;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import ru.education.screen.ChangeLevelScreen;
import ru.education.screen.GameScreen;
import ru.education.screen.GameScreenLvl2;
import ru.education.screen.GameScreenLvl3;
import ru.education.screen.MenuScreen;
import ru.education.screen.SettingsScreen;
import ru.education.service.MemoryService;
import ru.education.ui.ChangeLevelUserInterface;
import ru.education.ui.SettingsUserInterface;

public class MeowGame extends Game {
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 480;
    public static final String GAME = "Game";
    public static final String GAMELVL2 = "GameLvl2";
    public static final String GAMELVL3 = "GameLvl3";
    public static final String MENU = "Menu";
    public static final String CHANGELVL = "ChangeLvl";
    public static final String SETTINGS = "Settings";
    private MenuScreen menuScreen;
    private GameScreen gameScreen;
    private GameScreenLvl2 gameScreenLvl2;
    private GameScreenLvl3 gameScreenLvl3;
    private SettingsScreen settingsScreen;
    private ChangeLevelScreen changeLevelScreen;
    private SpriteBatch spriteBatch;
    private Array<Boolean> lockedLvls;
    private ChangeLevelUserInterface changeLevelUserInterface;
    private BitmapFont font;


    @Override
    public void create() {
        menuScreen = new MenuScreen(this);
        gameScreen = new GameScreen(this);
        gameScreenLvl2 = new GameScreenLvl2(this);
        gameScreenLvl3 = new GameScreenLvl3(this);
        changeLevelScreen = new ChangeLevelScreen(this);
        settingsScreen = new SettingsScreen(this);
        lockedLvls = new Array<>(3);
        if (MemoryService.getPreferences() == null) {
            lockedLvls.add(false);
            lockedLvls.add(true);
            lockedLvls.add(true);
        } else {
            lockedLvls.add(MemoryService.loadUnlocks((byte) 0));
            lockedLvls.add(MemoryService.loadUnlocks((byte) 1));
            lockedLvls.add(MemoryService.loadUnlocks((byte) 2));
        }

        SettingsUserInterface.isMusicOn = MemoryService.loadIsMusicOn();
        SettingsUserInterface.isSoundOn = MemoryService.loadIsSoundOn();

        spriteBatch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(2f);

        this.setScreen(menuScreen);
    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    public BitmapFont getFont() {
        return font;
    }

    public void changeScreen(String screenName) {
        switch (screenName) {
            case GAME      -> setScreen(gameScreen);
            case MENU      -> setScreen(menuScreen);
            case GAMELVL2  -> setScreen(gameScreenLvl2);
            case GAMELVL3  -> setScreen(gameScreenLvl3);
            case CHANGELVL -> setScreen(changeLevelScreen);
            case SETTINGS  -> setScreen(settingsScreen);
        }
    }

    public Array<Boolean> getLockedLvls() {
        return lockedLvls;
    }

    public void unlockLevel(byte numberLevel){
        lockedLvls.set(numberLevel, false);
    }

    public void lockLevel(byte numberLevel){
        lockedLvls.set(numberLevel, true);
    }


    @Override
    public void dispose() {
        super.dispose();

        MemoryService.saveUnlocks(lockedLvls);

        spriteBatch.dispose();
        font.dispose();

        menuScreen.dispose();
        gameScreen.dispose();
        gameScreenLvl2.dispose();
        gameScreenLvl3.dispose();
        changeLevelScreen.dispose();
        settingsScreen.dispose();

    }
}
