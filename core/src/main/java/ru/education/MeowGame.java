package ru.education;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ru.education.screen.GameScreen;
import ru.education.screen.GameScreenLvl2;
import ru.education.screen.MenuScreen;

public class MeowGame extends Game {
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 480;
    public static final String GAME = "Game";
    public static final String GAMELVL2 = "GameLvl2";
    public static final String MENU = "Menu";
    private MenuScreen menuScreen;
    private GameScreen gameScreen;
    private GameScreenLvl2 gameScreenLvl2;
    private SpriteBatch spriteBatch;
    private BitmapFont font;

    @Override
    public void create() {
        menuScreen = new MenuScreen(this);
        gameScreen = new GameScreen(this);
        gameScreenLvl2 = new GameScreenLvl2(this);

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
        if (screenName.equalsIgnoreCase(GAME)) {
            setScreen(gameScreen);
        } else if (screenName.equalsIgnoreCase(MENU)) {
            setScreen(menuScreen);
        } else if (screenName.equalsIgnoreCase(GAMELVL2)) {
            setScreen(gameScreenLvl2);
        }
    }

    @Override
    public void dispose() {
        super.dispose();

        spriteBatch.dispose();
        font.dispose();

        menuScreen.dispose();
        gameScreen.dispose();
        gameScreenLvl2.dispose();
    }
}
