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

public class LoseScreenUserInterface {
    private final ImageButton btnGoToMenu;
    private final MeowGame meowGame;
    private final Stage stage;
    private final OrthographicCamera camera;


    public LoseScreenUserInterface(OrthographicCamera camera, MeowGame meowGame) {
        this.meowGame = meowGame;
        this.camera = camera;


        Texture btnStartTexture = new Texture("UI/btn_try_lose.png");
        Drawable btnStartDrawable = new TextureRegionDrawable(btnStartTexture);
        btnGoToMenu = new ImageButton(btnStartDrawable);

        btnGoToMenu.setPosition(
            MeowGame.SCREEN_WIDTH / 2f - btnStartTexture.getWidth() / 2f + 10,
            MeowGame.SCREEN_HEIGHT / 2f - btnStartTexture.getHeight() / 2f - 65
        );


        btnGoToMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                meowGame.changeScreen(MeowGame.MENU);
            }
        });


        Viewport fitViewport = new StretchViewport(MeowGame.SCREEN_WIDTH, MeowGame.SCREEN_HEIGHT, camera);
        stage = new Stage(fitViewport);
        Gdx.input.setInputProcessor(stage);

        stage.addActor(btnGoToMenu);

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

