package ru.education;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import ru.education.screen.MenuScreen;

public class App extends Game {
    public static final int sc_wight = 800;
    public static final int sc_height = 480;
    public static final String game = "Game";
    public static final String menu = "Menu";
    private SpriteBatch batch;
    private MenuScreen menuScreen;

    @Override
    public void create() {
        batch = new SpriteBatch();
        menuScreen = new MenuScreen(this);

        this.setScreen(menuScreen);
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    @Override
    public void dispose() {
        super.dispose();

        batch.dispose();
        menuScreen.dispose();
    }
}
