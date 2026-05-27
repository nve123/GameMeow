package ru.education.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import ru.education.MeowGame;
import ru.education.screen.InfinityModeScreen;

public class InfSettingsUserInterface {
    private final ImageButton btnChangeBack1;
    private final ImageButton btnChangeBack2;
    private final ImageButton btnChangeBack3;
    private final ImageButton btnChangeCat1;
    private final ImageButton btnStart;
    private ImageButton returnBtn;
    private MeowGame meowGame;
    private final Stage stage;
    private final OrthographicCamera camera;
    private final InfinityModeScreen infinityModeScreen;
    public static Texture back;
    public static byte numBack;

    public InfSettingsUserInterface(OrthographicCamera camera, MeowGame meowGame){
        this.meowGame = new MeowGame();
        this.camera = camera;
        infinityModeScreen = new InfinityModeScreen(meowGame);

        btnChangeBack1 = new ImageButton(new TextureRegionDrawable(new Texture("UI/change_back1.png")));
        btnChangeBack2 = new ImageButton(new TextureRegionDrawable(new Texture("UI/change_back2.png")));
        btnChangeBack3 = new ImageButton(new TextureRegionDrawable(new Texture("UI/change_back3.png")));
        btnChangeCat1 = new ImageButton(new TextureRegionDrawable(new Texture("UI/cat_back1.png")));
        btnStart = new ImageButton(new TextureRegionDrawable(new Texture("UI/btn_start_inf.png")));
        returnBtn = new ImageButton(new TextureRegionDrawable(new Texture("UI/btn_return.png")));

        btnChangeBack1.setPosition(
            0 + 100 + 60,
            MeowGame.SCREEN_HEIGHT - 100
        );
        btnChangeBack2.setPosition(
            0 + 100 + 60 + 60 + 30,
            MeowGame.SCREEN_HEIGHT - 100
        );
        btnChangeBack3.setPosition(
            0 + 100 + 60 + 60 + 60 + 30 + 30,
            MeowGame.SCREEN_HEIGHT - 100
        );
        btnStart.setPosition(
            MeowGame.SCREEN_WIDTH / 2 - 51,
            MeowGame.SCREEN_HEIGHT / 2
        );
        btnChangeCat1.setPosition(
            0 + 100 + 60,
            MeowGame.SCREEN_HEIGHT / 2f - 100
        );
        returnBtn.setPosition(0, MeowGame.SCREEN_HEIGHT - 50);
        returnBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                meowGame.changeScreen(meowGame.MENU);
            }
        });

        btnChangeBack1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                back = new Texture(Gdx.files.internal("backgrounds/game_back_lvl1.png"));
                numBack = 1;
            }
        });
        btnStart.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                meowGame.changeScreen(MeowGame.INFMODE);
            }
        });
        btnChangeBack2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                back = new Texture(Gdx.files.internal("backgrounds/game_back_lvl2.png"));
                numBack = 2;
            }
        });
        btnChangeBack3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                back = new Texture(Gdx.files.internal("backgrounds/game_back_lvl3.png"));
                numBack = 3;
            }
        });

        Viewport fitViewport = new StretchViewport(MeowGame.SCREEN_WIDTH, MeowGame.SCREEN_HEIGHT, camera);
        stage = new Stage(fitViewport);
        Gdx.input.setInputProcessor(stage);

        stage.addActor(btnChangeBack1);
        stage.addActor(btnChangeBack2);
        stage.addActor(btnChangeBack3);
        stage.addActor(btnChangeCat1);
        stage.addActor(btnStart);
        stage.addActor(returnBtn);
    }

    public void drawUI() {
        stage.act();
        stage.draw();
    }

    public Texture getBack() {
        return back;
    }

    public void dispose() {
        stage.dispose();
    }
}
