package ru.education.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import ru.education.MeowGame;
import ru.education.service.MemoryService;

public class SettingsUserInterface {
    private TextButton musicSettingBtn;
    private TextButton soundSettingBtn;
    private ImageButton returnBtn;
    private ImageButton resetBtn;
    private MeowGame meowGame;
    public static boolean isMusicOn;
    public static boolean isSoundOn;
    private final Stage stage;
    private final OrthographicCamera camera;

    public SettingsUserInterface(OrthographicCamera camera, MeowGame meowGame) {
        this.meowGame = new MeowGame();
        this.camera = camera;
        Skin skin = new Skin();

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));

        skin.add("default", new BitmapFont());

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = skin.newDrawable("white", Color.DARK_GRAY);
        style.down = skin.newDrawable("white", Color.BLUE);
        style.font = skin.getFont("default");

        musicSettingBtn = new TextButton("music: " + MemoryService.loadIsMusicOn(), style);
        soundSettingBtn = new TextButton("sound: " + MemoryService.loadIsSoundOn(), style);
        Drawable resetDrawable = new TextureRegionDrawable(new Texture("btn_reset.png"));
        Drawable returnDrawable = new TextureRegionDrawable(new Texture("btn_return.png"));
        returnBtn = new ImageButton(returnDrawable);
        resetBtn = new ImageButton(resetDrawable);
        isSoundOn = MemoryService.loadIsMusicOn();
        isMusicOn = MemoryService.loadIsMusicOn();

        musicSettingBtn.setPosition(
            MeowGame.SCREEN_WIDTH / 2f - 50,
            MeowGame.SCREEN_HEIGHT / 2f - 50
        );
        soundSettingBtn.setPosition(
            MeowGame.SCREEN_WIDTH / 2f - 50,
            MeowGame.SCREEN_HEIGHT / 2f
        );
        returnBtn.setPosition(0, MeowGame.SCREEN_HEIGHT - 50);
        resetBtn.setPosition(MeowGame.SCREEN_WIDTH / 2f + 100, MeowGame.SCREEN_HEIGHT / 2f - 40);

        returnBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                meowGame.changeScreen(meowGame.MENU);
            }
        });

        resetBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                meowGame.lockLevel((byte) 1);
                meowGame.lockLevel((byte) 2);
                meowGame.getLockedLvls().add(MemoryService.loadUnlocks((byte) 1));
                meowGame.getLockedLvls().add(MemoryService.loadUnlocks((byte) 2));
            }
        });

        Viewport fitViewport = new StretchViewport(MeowGame.SCREEN_WIDTH, MeowGame.SCREEN_HEIGHT, camera);
        stage = new Stage(fitViewport);
        Gdx.input.setInputProcessor(stage);

        stage.addActor(musicSettingBtn);
        stage.addActor(soundSettingBtn);
        stage.addActor(returnBtn);
        stage.addActor(resetBtn);
    }

    public void handleInput() {
        if (Gdx.input.justTouched()) {
            if (returnBtn.isPressed()) {
                returnBtn.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        meowGame.changeScreen(meowGame.MENU);
                    }
                });
            }
            if (musicSettingBtn.isPressed()) {
                MemoryService.saveMusicSettings(!MemoryService.loadIsMusicOn());
                musicSettingBtn.setText("music: " + MemoryService.loadIsMusicOn());
                isMusicOn = MemoryService.loadIsMusicOn();
            }
            if (soundSettingBtn.isPressed()) {
                MemoryService.saveSoundSettings(!MemoryService.loadIsSoundOn());
                soundSettingBtn.setText("sound: " + MemoryService.loadIsSoundOn());
                isSoundOn = MemoryService.loadIsSoundOn();
            }
            if (resetBtn.isPressed()) {
                resetBtn.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        meowGame.lockLevel((byte) 1);
                        meowGame.lockLevel((byte) 2);
                        meowGame.getLockedLvls().add(MemoryService.loadUnlocks((byte) 1));
                        meowGame.getLockedLvls().add(MemoryService.loadUnlocks((byte) 2));
                    }
                });
            }
        }
    }

    public void drawUI() {
        stage.act();
        stage.draw();
        handleInput();
    }

    public void dispose() {
        stage.dispose();
    }
}


