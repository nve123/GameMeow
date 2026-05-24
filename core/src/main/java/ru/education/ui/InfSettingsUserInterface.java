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

        btnChangeBack1 = new ImageButton(new TextureRegionDrawable(new Texture("change_back1.png")));
        btnChangeBack2 = new ImageButton(new TextureRegionDrawable(new Texture("change_back2.png")));
        btnChangeBack3 = new ImageButton(new TextureRegionDrawable(new Texture("change_back3.png")));
        btnChangeCat1 = new ImageButton(new TextureRegionDrawable(new Texture("cat_back1.png")));
        btnStart = new ImageButton(new TextureRegionDrawable(new Texture("btn_start_inf.png")));

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

        btnChangeBack1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                back = new Texture(Gdx.files.internal("V31_ликиция.png"));
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
                back = new Texture(Gdx.files.internal("2_likizhiR_0001 (3).png"));
                numBack = 2;
            }
        });
        btnChangeBack3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                back = new Texture(Gdx.files.internal("3_iiii_0004 (5).png"));
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
