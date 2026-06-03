package ru.education.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import ru.education.camera.OrthographicCameraWithLeftRightState;
import ru.education.screen.GameScreenLvl1;

public class GameUserInterface {
    private final ImageButton btnGoToLeftScreen;
    private final ImageButton btnGoToRightScreen;
    private final OrthographicCameraWithLeftRightState camera;
    private Screen screen;
    private final Stage stage;
    MeowGame meowGame;
    private ImageButton returnBtn;

    public GameUserInterface(OrthographicCameraWithLeftRightState camera, Screen screen, MeowGame meowGame) {
        this.meowGame = meowGame;
        this.camera = camera;
        this.screen = screen;

        Drawable leftArrowDrawable = new TextureRegionDrawable(new Texture("UI/btn_to_left.png"));
        Drawable rightArrowDrawable = new TextureRegionDrawable(new Texture("UI/btn_to_right.png"));
        returnBtn = new ImageButton(new TextureRegionDrawable(new Texture("UI/btn_return.png")));
        btnGoToLeftScreen = new ImageButton(leftArrowDrawable);
        btnGoToRightScreen = new ImageButton(rightArrowDrawable);

        btnGoToLeftScreen.setPosition(
            MeowGame.SCREEN_WIDTH + 24,
            MeowGame.SCREEN_HEIGHT - btnGoToLeftScreen.getHeight() - 50
        );
        btnGoToRightScreen.setPosition(
            MeowGame.SCREEN_WIDTH - btnGoToRightScreen.getWidth() - 24,
            MeowGame.SCREEN_HEIGHT - btnGoToRightScreen.getHeight() - 50
        );

        btnGoToLeftScreen.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (camera.position.x > MeowGame.SCREEN_WIDTH / 2f) {
                    camera.moveCameraToLeft(MeowGame.SCREEN_WIDTH);
                }
            }
        });

        btnGoToRightScreen.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (camera.position.x < GameScreenLvl1.WORLD_WIDTH - MeowGame.SCREEN_WIDTH / 2f) {
                    camera.moveCameraToRight(MeowGame.SCREEN_WIDTH);
                }
            }
        });
        returnBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                meowGame.changeScreen(meowGame.MENU);
            }
        });
        returnBtn.setPosition(0, MeowGame.SCREEN_HEIGHT - 50);

        Viewport fitViewport = new StretchViewport(MeowGame.SCREEN_WIDTH, MeowGame.SCREEN_HEIGHT, camera);
        stage = new Stage(fitViewport);
        Gdx.input.setInputProcessor(stage);

        stage.addActor(btnGoToLeftScreen);
        stage.addActor(btnGoToRightScreen);
        stage.addActor(returnBtn);

    }

    public void drawUI() {
        stage.act();
        stage.draw();
    }

    public void dispose() {
        stage.dispose();
    }
}
