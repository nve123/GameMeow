package ru.education.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ru.education.MeowGame;
import ru.education.ui.ChangeLevelUserInterface;

public class ChangeLevelScreen implements Screen {
    private final MeowGame meowGame;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Texture background;
    private ChangeLevelUserInterface changeLevelUserInterface;
    public Music backgroundMusic;

    public ChangeLevelScreen(MeowGame meowGame) {this.meowGame = meowGame;}

    @Override
    public void show() {
        batch = meowGame.getSpriteBatch();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, MeowGame.SCREEN_WIDTH, MeowGame.SCREEN_HEIGHT);

        background = new Texture(Gdx.files.internal("backgrounds/choice_lvl_back.png"));
        backgroundMusic = null;
        //backgroundMusic.setLooping(true);
        //backgroundMusic.setVolume(0.0f);
        //backgroundMusic.play();

        changeLevelUserInterface = new ChangeLevelUserInterface(
            meowGame,
            camera
        );
        changeLevelUserInterface.updateButtonsState();

    }

    @Override
    public void render(float delta) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(background, 0, 0, MeowGame.SCREEN_WIDTH, MeowGame.SCREEN_HEIGHT);
        batch.end();
        changeLevelUserInterface.drawUI();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        //backgroundMusic.dispose();
        background.dispose();
        changeLevelUserInterface.dispose();
    }
}
