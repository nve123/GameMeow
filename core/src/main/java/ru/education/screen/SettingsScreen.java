package ru.education.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

import javax.swing.Timer;

import ru.education.MeowGame;
import ru.education.service.MemoryService;
import ru.education.ui.SettingsUserInterface;

public class SettingsScreen implements Screen {
    private final MeowGame meowGame;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Texture background;
    private SettingsUserInterface settingsUserInterface;
    private Music backgroundMusic;

    public SettingsScreen(MeowGame meowGame) {
        this.meowGame = meowGame;
    }

    @Override
    public void show() {
        batch = meowGame.getSpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, MeowGame.SCREEN_WIDTH, MeowGame.SCREEN_HEIGHT);
        background = new Texture(Gdx.files.internal("backgrounds/change_lvl_back.png"));
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/da882ce11f7c88f.mp3"));;
        if (SettingsUserInterface.isMusicOn) {
            backgroundMusic.setLooping(true);
            backgroundMusic.setVolume(0.2f);
            backgroundMusic.play();
        }
        settingsUserInterface =new SettingsUserInterface(camera, meowGame);

    }

    @Override
    public void render(float delta) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(background, 0, 0, MeowGame.SCREEN_WIDTH, MeowGame.SCREEN_HEIGHT);
        batch.end();
        settingsUserInterface.drawUI();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        backgroundMusic.stop();
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        backgroundMusic.stop();
    }

    @Override
    public void dispose() {
        background.dispose();
        settingsUserInterface.dispose();
        background.dispose();
    }
}
