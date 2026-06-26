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
    private final Stage stage;
    private final ImageButton btnSettings;
    private final ImageButton btnInf;
    private final ImageButton btnExit;
    private final OrthographicCamera camera;
    private final BtnStartListener btnStartListener;

    public MenuUserInterface(OrthographicCamera camera, BtnStartListener btnStartListener, MeowGame meowGame) {
        this.meowGame = meowGame;
        this.camera = camera;
        this.btnStartListener = btnStartListener;

        Texture btnStartTexture = new Texture("UI/btn_start.png");
        Texture btnExitTexture = new Texture("UI/btn_exit.png");
        Drawable btnStartDrawable = new TextureRegionDrawable(btnStartTexture);
        Drawable btnExitDrawable = new TextureRegionDrawable(btnExitTexture);
        btnStart = new ImageButton(btnStartDrawable);
        btnExit = new ImageButton(btnExitDrawable);
        btnSettings = new ImageButton(new TextureRegionDrawable(new Texture("UI/btn_settings.png")));
        btnInf = new ImageButton(new TextureRegionDrawable(new Texture("UI/btn_start_Infinity.png")));

        btnStart.setPosition(
            MeowGame.SCREEN_WIDTH / 2f - btnStartTexture.getWidth() / 2f + 10,
            MeowGame.SCREEN_HEIGHT / 2f - btnStartTexture.getHeight() / 2f - 65
        );

        btnInf.setPosition(
            MeowGame.SCREEN_WIDTH / 2f - btnStartTexture.getWidth() / 2f + 10,
            MeowGame.SCREEN_HEIGHT / 2f - btnStartTexture.getHeight() / 2f - 65 - 100
        );
        btnExit.setPosition(
            MeowGame.SCREEN_WIDTH - btnExitTexture.getWidth() - 12,
            MeowGame.SCREEN_HEIGHT - btnExitTexture.getHeight() - 12
        );

        btnSettings.setPosition(0 + 12, MeowGame.SCREEN_HEIGHT - 50 - 12);

        btnSettings.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                meowGame.changeScreen(MeowGame.SETTINGS);
            }
        });

        btnInf.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                meowGame.changeScreen(MeowGame.INFMODESETTINGS);
            }
        });
        btnExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
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
        stage.addActor(btnInf);
        stage.addActor(btnExit);
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
