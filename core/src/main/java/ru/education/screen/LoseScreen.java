package ru.education.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ru.education.MeowGame;
import ru.education.fontBuilder.FontBuilder;
import ru.education.service.MemoryService;
import ru.education.ui.LoseScreenUserInterface;
import ru.education.ui.SettingsUserInterface;

public class LoseScreen implements Screen {
    private final MeowGame meowGame;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Texture background;
    private LoseScreenUserInterface loseScreenUserInterface;
    private BitmapFont font;

    public LoseScreen(MeowGame meowGame) {
        this.meowGame = meowGame;
    }


    @Override
    public void show() {
        batch = meowGame.getSpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, MeowGame.SCREEN_WIDTH, MeowGame.SCREEN_HEIGHT);
        font = FontBuilder.generate(48, Color.BLACK, "fonts/Curtsweeper-Regular.otf");
        background = new Texture(Gdx.files.internal("backgrounds/settings_back.png"));
        loseScreenUserInterface = new LoseScreenUserInterface(camera, meowGame);
    }

    @Override
    public void render(float delta) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(background, 0, 0, MeowGame.SCREEN_WIDTH, MeowGame.SCREEN_HEIGHT);
        font.draw(batch, "You lost", MeowGame.SCREEN_WIDTH / 2f - 306 / 2f + 10 - 35,
            MeowGame.SCREEN_HEIGHT / 2f - 73 / 2f - 65 + 200);
        batch.end();
        loseScreenUserInterface.drawUI();
    }

    @Override
    public void resize(int width, int height) {
        MemoryService.saveUnlocks(meowGame.getLockedLvls());
    }

    @Override
    public void pause() {
        MemoryService.saveUnlocks(meowGame.getLockedLvls());
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        MemoryService.saveUnlocks(meowGame.getLockedLvls());
    }

    @Override
    public void dispose() {
        MemoryService.saveUnlocks(meowGame.getLockedLvls());
        background.dispose();
        loseScreenUserInterface.dispose();
        background.dispose();
        font.dispose();
    }
}
