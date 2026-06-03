package ru.education.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
    private final ImageButton btnChangeBack5;
    private final ImageButton btnChangeBack4;
    private final ImageButton btnChangeCat2;
    private final ImageButton btnChangeCat3;
    private final ImageButton btnChangeCat4;
    private final ImageButton btnChangeCat5;
    private final ImageButton btnChangeCat6;
    private ImageButton returnBtn;
    private MeowGame meowGame;
    private final Stage stage;
    private final OrthographicCamera camera;
    private final InfinityModeScreen infinityModeScreen;
    public static Texture back;
    public static byte numBack;
    public static byte catBack;

    public InfSettingsUserInterface(OrthographicCamera camera, MeowGame meowGame){
        this.meowGame = new MeowGame();
        this.camera = camera;
        infinityModeScreen = new InfinityModeScreen(meowGame);

        btnChangeBack1 = new ImageButton(new TextureRegionDrawable(new Texture("UI/change_back1.png")));
        btnChangeBack2 = new ImageButton(new TextureRegionDrawable(new Texture("UI/change_back2.png")));
        btnChangeBack3 = new ImageButton(new TextureRegionDrawable(new Texture("UI/change_back3.png")));
        btnChangeBack4 = new ImageButton(new TextureRegionDrawable(new Texture("UI/change_back4.png")));
        btnChangeBack5 = new ImageButton(new TextureRegionDrawable(new Texture("UI/change_back5.png")));
        btnChangeCat1 = new ImageButton(new TextureRegionDrawable(new Texture("UI/cat_back1.png")));
        btnChangeCat2 = new ImageButton(new TextureRegionDrawable(new Texture("UI/cat_back2.png")));
        btnChangeCat3 = new ImageButton(new TextureRegionDrawable(new Texture("UI/cat_back3.png")));
        btnChangeCat4 = new ImageButton(new TextureRegionDrawable(new Texture("UI/cat_back4.png")));
        btnChangeCat5 = new ImageButton(new TextureRegionDrawable(new Texture("UI/cat_back5.png")));
        btnChangeCat6 = new ImageButton(new TextureRegionDrawable(new Texture("UI/cat_back6.png")));
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
        btnChangeBack4.setPosition(
            0 + 100 + 60 + 60 + 60 + 60 + 30 + 30 + 30,
            MeowGame.SCREEN_HEIGHT - 100
        );
        btnChangeBack5.setPosition(
            0 + 100 + 60 + 60 + 60 + 60 + 60 + 30 + 30 + 30 + 30,
            MeowGame.SCREEN_HEIGHT - 100
        );
        btnStart.setPosition(
            MeowGame.SCREEN_WIDTH - 51,
            MeowGame.SCREEN_HEIGHT / 2
        );
        btnChangeCat1.setPosition(
            0 + 100 + 60,
            MeowGame.SCREEN_HEIGHT / 2f - 100
        );
        btnChangeCat2.setPosition(
            0 + 100 + 60 + 35 + 30,
            MeowGame.SCREEN_HEIGHT / 2f - 100
        );
        btnChangeCat3.setPosition(
            0 + 100 + 60 + 35 + 35 + 30 + 30,
            MeowGame.SCREEN_HEIGHT / 2f - 100
        );
        btnChangeCat4.setPosition(
            0 + 100 + 60 + 35 + 35 + 35 + 30 + 30 + 30,
            MeowGame.SCREEN_HEIGHT / 2f - 100
        );
        btnChangeCat5.setPosition(
            0 + 100 + 60 + 35 + 35 + 35 + 35 + 30 + 30 + 30 + 30,
            MeowGame.SCREEN_HEIGHT / 2f - 100
        );
        btnChangeCat6.setPosition(
            0 + 100 + 60 + 35 + 35 + 35 + 35 + 45 + 30 + 30 + 30 + 30 + 30,
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
        }
        );btnChangeBack4.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                back = new Texture(Gdx.files.internal("backgrounds/game_back_lvl4.png"));
                numBack = 4;
            }

        });btnChangeBack5.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                back = new Texture(Gdx.files.internal("backgrounds/game_back_lvl5.png"));
                numBack = 5;
            }
        });
        btnChangeCat1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                catBack = 1;
            }
        });
        btnChangeCat2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                catBack = 2;
            }
        });
        btnChangeCat3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                catBack = 3;
            }
        });
        btnChangeCat4.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                catBack = 4;
            }
        });
        btnChangeCat5.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                catBack = 5;
            }
        });
        btnChangeCat6.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                catBack = 6;
            }
        });

        Viewport fitViewport = new StretchViewport(MeowGame.SCREEN_WIDTH, MeowGame.SCREEN_HEIGHT, camera);
        stage = new Stage(fitViewport);
        Gdx.input.setInputProcessor(stage);

        stage.addActor(btnChangeBack1);
        stage.addActor(btnChangeBack2);
        stage.addActor(btnChangeBack3);
        stage.addActor(btnChangeBack4);
        stage.addActor(btnChangeBack5);
        stage.addActor(btnChangeCat1);
        stage.addActor(btnChangeCat2);
        stage.addActor(btnChangeCat3);
        stage.addActor(btnChangeCat4);
        stage.addActor(btnChangeCat5);
        stage.addActor(btnChangeCat6);
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
