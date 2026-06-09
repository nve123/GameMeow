package ru.education.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ru.education.MeowGame;
import ru.education.fontBuilder.FontBuilder;
import ru.education.ui.InfSettingsUserInterface;
import ru.education.ui.SettingsUserInterface;

public class InfinityModeSettingsScreen implements Screen {
    private final MeowGame meowGame;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Texture background;
    private InfSettingsUserInterface infSettingsUserInterface;
    private BitmapFont font;

    public InfinityModeSettingsScreen(MeowGame meowGame) {
        this.meowGame = meowGame;
    }

    @Override
    public void show() {
        batch = meowGame.getSpriteBatch();
        font = FontBuilder.generate(12, Color.BLACK, "fonts/Curtsweeper-Regular.otf");
        camera = new OrthographicCamera();
        camera.setToOrtho(false, MeowGame.SCREEN_WIDTH, MeowGame.SCREEN_HEIGHT);
        background = new Texture(Gdx.files.internal("backgrounds/change_lvl_back.png"));
        infSettingsUserInterface = new InfSettingsUserInterface(camera, meowGame);
    }

    @Override
    public void render(float delta) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(background, 0, 0, MeowGame.SCREEN_WIDTH, MeowGame.SCREEN_HEIGHT);
        font.draw(batch, "Tap to select cat skin and map layout", 180, 480 - 100);
        batch.end();
        infSettingsUserInterface.drawUI();
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
        background.dispose();
        font.dispose();
    }

}
