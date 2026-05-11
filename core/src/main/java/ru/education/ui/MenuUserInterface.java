package ru.education.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import ru.education.MeowGame;

public class MenuUserInterface {
    private final ImageButton btnStart;
    private final MeowGame meowGame;
    private final Music backgroundMusic;
    private final Stage stage;
    private final ImageButton btnSettings;
    private final OrthographicCamera camera;
    private final BtnStartListener btnStartListener;

    public MenuUserInterface(OrthographicCamera camera, BtnStartListener btnStartListener, MeowGame meowGame,Music backgroundMusic) {
        this.backgroundMusic = backgroundMusic;
        this.meowGame = meowGame;
        this.camera = camera;
        this.btnStartListener = btnStartListener;

        Texture btnStartTexture = new Texture("btn_start.png");
        Drawable btnStartDrawable = new TextureRegionDrawable(btnStartTexture);
        Drawable settingsDrawable = new TextureRegionDrawable(new Texture("btn_settings.png"));
        btnStart = new ImageButton(btnStartDrawable);
        btnSettings = new ImageButton(settingsDrawable);

        btnStart.setPosition(
            MeowGame.SCREEN_WIDTH / 2f - btnStartTexture.getWidth() / 2f,
            MeowGame.SCREEN_HEIGHT / 2f - btnStartTexture.getHeight() / 2f
        );

        btnSettings.setPosition(0, MeowGame.SCREEN_HEIGHT - 50);

        btnSettings.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pauseMusic(backgroundMusic);
                meowGame.changeScreen(MeowGame.SETTINGS);
            }
        });

        btnStart.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                btnStartListener.onClick();
            }
        });

        Viewport fitViewport = new StretchViewport(MeowGame.SCREEN_WIDTH, MeowGame.SCREEN_HEIGHT, camera);
        stage = new Stage(fitViewport);
        Gdx.input.setInputProcessor(stage);

        stage.addActor(btnStart);
        stage.addActor(btnSettings);
    }

    public void drawUI() {
        stage.act();
        stage.draw();
    }

    public void pauseMusic(Music backgroundMusic){
        backgroundMusic.stop();
    }

    public void dispose() {
        stage.dispose();
    }
}
