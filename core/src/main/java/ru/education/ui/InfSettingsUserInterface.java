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
    private final TextureRegionDrawable textureDefoultBtnChangeBack1;
    private final TextureRegionDrawable textureClickedBtnChangeBack1;
    private final ImageButton btnChangeBack2;
    private final TextureRegionDrawable textureDefoultBtnChangeBack2;
    private final TextureRegionDrawable textureClickedBtnChangeBack2;
    private final ImageButton btnChangeBack3;
    private final TextureRegionDrawable textureDefoultBtnChangeBack3;
    private final TextureRegionDrawable textureClickedBtnChangeBack3;
    private final ImageButton btnChangeCat1;
    private final TextureRegionDrawable textureDefoultBtnChangeCat1;
    private final TextureRegionDrawable textureClickedBtnChangeCat1;
    private final ImageButton btnStart;
    private final ImageButton btnChangeBack5;
    private final TextureRegionDrawable textureDefoultBtnChangeBack5;
    private final TextureRegionDrawable textureClickedBtnChangeBack5;
    private final ImageButton btnChangeBack4;
    private final TextureRegionDrawable textureDefoultBtnChangeBack4;
    private final TextureRegionDrawable textureClickedBtnChangeBack4;
    private final ImageButton btnChangeCat2;
    private final TextureRegionDrawable textureDefoultBtnChangeCat2;
    private final TextureRegionDrawable textureClickedBtnChangeCat2;
    private final ImageButton btnChangeCat3;
    private final TextureRegionDrawable textureDefoultBtnChangeCat3;
    private final TextureRegionDrawable textureClickedBtnChangeCat3;
    private final ImageButton btnChangeCat4;
    private final TextureRegionDrawable textureDefoultBtnChangeCat4;
    private final TextureRegionDrawable textureClickedBtnChangeCat4;
    private final ImageButton btnChangeCat5;
    private final TextureRegionDrawable textureDefoultBtnChangeCat5;
    private final TextureRegionDrawable textureClickedBtnChangeCat5;
    private final ImageButton btnChangeCat6;
    private final TextureRegionDrawable textureDefoultBtnChangeCat6;
    private final TextureRegionDrawable textureClickedBtnChangeCat6;
    private ImageButton returnBtn;
    private MeowGame meowGame;
    private final Stage stage;
    private final OrthographicCamera camera;
    private final InfinityModeScreen infinityModeScreen;
    public static Texture back = new Texture(Gdx.files.internal("backgrounds/game_back_lvl1.png"));
    public static byte numBack = 1;
    public static byte catBack = 1;

    public InfSettingsUserInterface(OrthographicCamera camera, MeowGame meowGame) {
        this.meowGame = new MeowGame();
        this.camera = camera;
        infinityModeScreen = new InfinityModeScreen(meowGame);
        textureDefoultBtnChangeBack1 = new TextureRegionDrawable(new Texture("UI/change_back1.png"));
        textureDefoultBtnChangeBack2 = new TextureRegionDrawable(new Texture("UI/change_back2.png"));
        textureDefoultBtnChangeBack3 = new TextureRegionDrawable(new Texture("UI/change_back3.png"));
        textureDefoultBtnChangeBack4 = new TextureRegionDrawable(new Texture("UI/change_back4.png"));
        textureDefoultBtnChangeBack5 = new TextureRegionDrawable(new Texture("UI/change_back5.png"));
        textureClickedBtnChangeBack1 = new TextureRegionDrawable(new Texture("UI/change_back1_clicked.png"));
        textureClickedBtnChangeBack2 = new TextureRegionDrawable(new Texture("UI/change_back2_clicked.png"));
        textureClickedBtnChangeBack3 = new TextureRegionDrawable(new Texture("UI/change_back3_clicked.png"));
        textureClickedBtnChangeBack4 = new TextureRegionDrawable(new Texture("UI/change_back4_clicked.png"));
        textureClickedBtnChangeBack5 = new TextureRegionDrawable(new Texture("UI/change_back5_clicked.png"));
        textureDefoultBtnChangeCat1 = new TextureRegionDrawable(new Texture("UI/cat_back1.png"));
        textureDefoultBtnChangeCat2 = new TextureRegionDrawable(new Texture("UI/cat_back2.png"));
        textureDefoultBtnChangeCat3 = new TextureRegionDrawable(new Texture("UI/cat_back3.png"));
        textureDefoultBtnChangeCat4 = new TextureRegionDrawable(new Texture("UI/cat_back4.png"));
        textureDefoultBtnChangeCat5 = new TextureRegionDrawable(new Texture("UI/cat_back5.png"));
        textureDefoultBtnChangeCat6 = new TextureRegionDrawable(new Texture("UI/cat_back6.png"));
        textureClickedBtnChangeCat1 = new TextureRegionDrawable(new Texture("UI/cat_back1_clicked.png"));
        textureClickedBtnChangeCat2 = new TextureRegionDrawable(new Texture("UI/cat_back2_clicked.png"));
        textureClickedBtnChangeCat3 = new TextureRegionDrawable(new Texture("UI/cat_back3_clicked.png"));
        textureClickedBtnChangeCat4 = new TextureRegionDrawable(new Texture("UI/cat_back4_clicked.png"));
        textureClickedBtnChangeCat5 = new TextureRegionDrawable(new Texture("UI/cat_back5_clicked.png"));
        textureClickedBtnChangeCat6 = new TextureRegionDrawable(new Texture("UI/cat_back6_clicked.png"));

        btnChangeBack1 = new ImageButton(textureDefoultBtnChangeBack1);
        ImageButton.ImageButtonStyle styleBtnLvl1 = new ImageButton.ImageButtonStyle();
        styleBtnLvl1.imageUp = textureDefoultBtnChangeBack1;
        styleBtnLvl1.imageChecked = textureClickedBtnChangeBack1;
        btnChangeBack1.setStyle(styleBtnLvl1);

        btnChangeBack2 = new ImageButton(textureDefoultBtnChangeBack2);
        ImageButton.ImageButtonStyle styleBtnLvl2 = new ImageButton.ImageButtonStyle();
        styleBtnLvl2.imageUp = textureDefoultBtnChangeBack2;
        styleBtnLvl2.imageChecked = textureClickedBtnChangeBack2;
        btnChangeBack2.setStyle(styleBtnLvl2);

        btnChangeBack3 = new ImageButton(textureDefoultBtnChangeBack3);
        ImageButton.ImageButtonStyle styleBtnLvl3 = new ImageButton.ImageButtonStyle();
        styleBtnLvl3.imageUp = textureDefoultBtnChangeBack3;
        styleBtnLvl3.imageChecked = textureClickedBtnChangeBack3;
        btnChangeBack3.setStyle(styleBtnLvl3);

        btnChangeBack4 = new ImageButton(textureDefoultBtnChangeBack4);
        ImageButton.ImageButtonStyle styleBtnLvl4 = new ImageButton.ImageButtonStyle();
        styleBtnLvl4.imageUp = textureDefoultBtnChangeBack4;
        styleBtnLvl4.imageChecked = textureClickedBtnChangeBack4;
        btnChangeBack4.setStyle(styleBtnLvl4);

        btnChangeBack5 = new ImageButton(textureDefoultBtnChangeBack5);
        ImageButton.ImageButtonStyle styleBtnLvl5 = new ImageButton.ImageButtonStyle();
        styleBtnLvl5.imageUp = textureDefoultBtnChangeBack5;
        styleBtnLvl5.imageChecked = textureClickedBtnChangeBack5;
        btnChangeBack5.setStyle(styleBtnLvl5);

        btnChangeCat1 = new ImageButton(textureDefoultBtnChangeCat1);
        ImageButton.ImageButtonStyle styleBtnCat1 = new ImageButton.ImageButtonStyle();
        styleBtnCat1.imageUp = textureDefoultBtnChangeCat1;
        styleBtnCat1.imageChecked = textureClickedBtnChangeCat1;
        btnChangeCat1.setStyle(styleBtnCat1);

        btnChangeCat2 = new ImageButton(textureDefoultBtnChangeCat2);
        ImageButton.ImageButtonStyle styleBtnCat2 = new ImageButton.ImageButtonStyle();
        styleBtnCat2.imageUp = textureDefoultBtnChangeCat2;
        styleBtnCat2.imageChecked = textureClickedBtnChangeCat2;
        btnChangeCat2.setStyle(styleBtnCat2);

        btnChangeCat3 = new ImageButton(textureDefoultBtnChangeCat3);
        ImageButton.ImageButtonStyle styleBtnCat3 = new ImageButton.ImageButtonStyle();
        styleBtnCat3.imageUp = textureDefoultBtnChangeCat3;
        styleBtnCat3.imageChecked = textureClickedBtnChangeCat3;
        btnChangeCat3.setStyle(styleBtnCat3);

        btnChangeCat4 = new ImageButton(textureDefoultBtnChangeCat4);
        ImageButton.ImageButtonStyle styleBtnCat4 = new ImageButton.ImageButtonStyle();
        styleBtnCat4.imageUp = textureDefoultBtnChangeCat4;
        styleBtnCat4.imageChecked = textureClickedBtnChangeCat4;
        btnChangeCat4.setStyle(styleBtnCat4);

        btnChangeCat5 = new ImageButton(textureDefoultBtnChangeCat5);
        ImageButton.ImageButtonStyle styleBtnCat5 = new ImageButton.ImageButtonStyle();
        styleBtnCat5.imageUp = textureDefoultBtnChangeCat5;
        styleBtnCat5.imageChecked = textureClickedBtnChangeCat5;
        btnChangeCat5.setStyle(styleBtnCat5);

        btnChangeCat6 = new ImageButton(textureDefoultBtnChangeCat6);
        ImageButton.ImageButtonStyle styleBtnCat6 = new ImageButton.ImageButtonStyle();
        styleBtnCat6.imageUp = textureDefoultBtnChangeCat6;
        styleBtnCat6.imageChecked = textureClickedBtnChangeCat6;
        btnChangeCat6.setStyle(styleBtnCat6);
        btnChangeCat1.setChecked(true);
        btnChangeBack1.setChecked(true);

        btnStart = new ImageButton(new TextureRegionDrawable(new Texture("UI/btn_start_inf.png")));
        returnBtn = new ImageButton(new TextureRegionDrawable(new Texture("UI/btn_return.png")));

        btnChangeBack1.setPosition(
            200 - 10,
            MeowGame.SCREEN_HEIGHT - 210
        );
        btnChangeBack2.setPosition(
            200 - 10 + 60 + 30,
            MeowGame.SCREEN_HEIGHT - 210
        );
        btnChangeBack3.setPosition(
            200 - 10 + 60 + 60 + 30 + 30,
            MeowGame.SCREEN_HEIGHT - 210
        );
        btnChangeBack4.setPosition(
            200 - 10 + 60 + 60 + 60 + 30 + 30 + 30,
            MeowGame.SCREEN_HEIGHT - 210
        );
        btnChangeBack5.setPosition(
            200 - 10 + 60 + 60 + 60 + 60 + 30 + 30 + 30 + 30,
            MeowGame.SCREEN_HEIGHT - 210
        );
        btnStart.setPosition(
            300,
            MeowGame.SCREEN_HEIGHT - 410
        );
        btnChangeCat1.setPosition(
            210,
            MeowGame.SCREEN_HEIGHT - 280
        );
        btnChangeCat2.setPosition(
            210 + 35 + 30,
            MeowGame.SCREEN_HEIGHT - 280
        );
        btnChangeCat3.setPosition(
            210 + 35 + 35 + 30 + 30,
            MeowGame.SCREEN_HEIGHT - 280
        );
        btnChangeCat4.setPosition(
            210 + 35 + 35 + 35 + 30 + 30 + 30,
            MeowGame.SCREEN_HEIGHT - 280
        );
        btnChangeCat5.setPosition(
            210 + 35 + 35 + 35 + 35 + 30 + 30 + 30 + 30,
            MeowGame.SCREEN_HEIGHT - 280
        );
        btnChangeCat6.setPosition(
            210 + 35 + 35 + 35 + 35 + 45 + 30 + 30 + 30 + 30 + 30,
            MeowGame.SCREEN_HEIGHT - 280
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
                resetTextureBtnLvlBack();
                back = new Texture(Gdx.files.internal("backgrounds/game_back_lvl1.png"));
                numBack = 1;
                btnChangeBack1.setChecked(true);
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
                resetTextureBtnLvlBack();
                back = new Texture(Gdx.files.internal("backgrounds/game_back_lvl2.png"));
                numBack = 2;
                btnChangeBack2.setChecked(true);
            }
        });
        btnChangeBack3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                resetTextureBtnLvlBack();
                back = new Texture(Gdx.files.internal("backgrounds/game_back_lvl3.png"));
                numBack = 3;
                btnChangeBack3.setChecked(true);
            }
        });
        btnChangeBack4.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                resetTextureBtnLvlBack();
                back = new Texture(Gdx.files.internal("backgrounds/game_back_lvl4.png"));
                numBack = 4;
                btnChangeBack4.setChecked(true);
            }

        });
        btnChangeBack5.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                resetTextureBtnLvlBack();
                back = new Texture(Gdx.files.internal("backgrounds/game_back_lvl5.png"));
                numBack = 5;
                btnChangeBack5.setChecked(true);
            }
        });
        btnChangeCat1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                resetTextureBtnCatBack();
                catBack = 1;
                btnChangeCat1.setChecked(true);
            }
        });
        btnChangeCat2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                resetTextureBtnCatBack();
                catBack = 2;
                btnChangeCat2.setChecked(true);
            }
        });
        btnChangeCat3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                resetTextureBtnCatBack();
                catBack = 3;
                btnChangeCat3.setChecked(true);
            }
        });
        btnChangeCat4.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                resetTextureBtnCatBack();
                catBack = 4;
                btnChangeCat4.setChecked(true);
            }
        });
        btnChangeCat5.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                resetTextureBtnCatBack();
                catBack = 5;
                btnChangeCat5.setChecked(true);
            }
        });
        btnChangeCat6.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                resetTextureBtnCatBack();
                catBack = 6;
                btnChangeCat6.setChecked(true);

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

    private void resetTextureBtnLvlBack() {
        btnChangeBack1.setChecked(false);
        btnChangeBack2.setChecked(false);
        btnChangeBack3.setChecked(false);
        btnChangeBack4.setChecked(false);
        btnChangeBack5.setChecked(false);
    }

    private void resetTextureBtnCatBack() {
        btnChangeCat6.setChecked(false);
        btnChangeCat5.setChecked(false);
        btnChangeCat4.setChecked(false);
        btnChangeCat3.setChecked(false);
        btnChangeCat2.setChecked(false);
        btnChangeCat1.setChecked(false);
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
