package ru.education;

import static ru.education.ui.InfSettingsUserInterface.numBack;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import ru.education.screen.ChangeLevelScreen;
import ru.education.screen.GameScreenLvl1;
import ru.education.screen.GameScreenLvl2;
import ru.education.screen.GameScreenLvl3;
import ru.education.screen.GameScreenLvl4;
import ru.education.screen.GameScreenLvl5;
import ru.education.screen.InfinityModeScreen;
import ru.education.screen.InfinityModeSettingsScreen;
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
    public static final String GAMELVL4 = "GameLvl4";
    public static final String GAMELVL5 = "GameLvl5";
    public static final String MENU = "Menu";
    public static final String CHANGELVL = "ChangeLvl";
    public static final String SETTINGS = "Settings";
    public static final String INFMODESETTINGS = "InfModeSettings";
    public static final String INFMODE = "InfMode";
    private MenuScreen menuScreen;
    private GameScreenLvl1 gameScreenLvl1;
    private GameScreenLvl2 gameScreenLvl2;
    private GameScreenLvl3 gameScreenLvl3;
    private GameScreenLvl4 gameScreenLvl4;
    private GameScreenLvl5 gameScreenLvl5;
    private SettingsScreen settingsScreen;
    private ChangeLevelScreen changeLevelScreen;
    private InfinityModeSettingsScreen infinityModeSettingsScreen;
    private InfinityModeScreen infinityModeScreen;
    private SpriteBatch spriteBatch;
    private Array<Boolean> lockedLvls;
    private BitmapFont font;
    public static byte numLvl;
    public Music backgroundMusic;


    @Override
    public void create() {
        menuScreen = new MenuScreen(this);
        gameScreenLvl1 = new GameScreenLvl1(this);
        gameScreenLvl2 = new GameScreenLvl2(this);
        gameScreenLvl3 = new GameScreenLvl3(this);
        gameScreenLvl4 = new GameScreenLvl4(this);
        gameScreenLvl5 = new GameScreenLvl5(this);
        changeLevelScreen = new ChangeLevelScreen(this);
        settingsScreen = new SettingsScreen(this);
        infinityModeSettingsScreen = new InfinityModeSettingsScreen(this);
        infinityModeScreen = new InfinityModeScreen(this);
        lockedLvls = new Array<>(5);
        if (MemoryService.getPreferences() == null) {
            lockedLvls.add(false);
            lockedLvls.add(true);
            lockedLvls.add(true);
            lockedLvls.add(true);
            lockedLvls.add(true);
        } else {
            lockedLvls.add(MemoryService.loadUnlocks((byte) 0));
            lockedLvls.add(MemoryService.loadUnlocks((byte) 1));
            lockedLvls.add(MemoryService.loadUnlocks((byte) 2));
            lockedLvls.add(MemoryService.loadUnlocks((byte) 3));
            lockedLvls.add(MemoryService.loadUnlocks((byte) 4));
        }

        SettingsUserInterface.isMusicOn = MemoryService.loadIsMusicOn();
        SettingsUserInterface.isSoundOn = MemoryService.loadIsSoundOn();

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/da882ce11f7c88f.mp3"));;
        if (SettingsUserInterface.isMusicOn) {
            backgroundMusic.setLooping(true);
            backgroundMusic.setVolume(0.2f);
            backgroundMusic.play();
        }

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
            case GAME            -> {
                setScreen(gameScreenLvl1);
                numLvl = 1;
            }
            case MENU            -> setScreen(menuScreen);
            case GAMELVL2        -> {
                setScreen(gameScreenLvl2);
                numLvl = 2;
            }
            case GAMELVL3        -> {
                setScreen(gameScreenLvl3);
                numLvl = 3;
            }
            case GAMELVL4        -> {
                setScreen(gameScreenLvl4);
                numLvl = 4;
            }
            case GAMELVL5        -> {
                setScreen(gameScreenLvl5);
                numLvl = 5;
            }
            case CHANGELVL       -> setScreen(changeLevelScreen);
            case SETTINGS        -> setScreen(settingsScreen);
            case INFMODESETTINGS -> setScreen(infinityModeSettingsScreen);
            case INFMODE         -> {
                setScreen(infinityModeScreen);
                numLvl = numBack;
            }
        }
    }

    public Array<Boolean> getLockedLvls() {
        return lockedLvls;
    }

    public void unlockLevel(byte numberLevel) {
        lockedLvls.set(numberLevel, false);
    }

    public void lockLevel(byte numberLevel) {
        lockedLvls.set(numberLevel, true);
    }


    @Override
    public void dispose() {
        super.dispose();

        MemoryService.saveUnlocks(lockedLvls);
        backgroundMusic.stop();
        backgroundMusic.dispose();

        spriteBatch.dispose();
        font.dispose();

        menuScreen.dispose();
        gameScreenLvl1.dispose();
        gameScreenLvl2.dispose();
        gameScreenLvl3.dispose();
        gameScreenLvl4.dispose();
        gameScreenLvl5.dispose();
        infinityModeSettingsScreen.dispose();
        infinityModeScreen.dispose();
        changeLevelScreen.dispose();
        settingsScreen.dispose();

    }
}
