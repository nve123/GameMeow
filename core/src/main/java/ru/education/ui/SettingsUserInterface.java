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
    private ImageButton musicSettingBtn;
    private ImageButton soundSettingBtn;
    private ImageButton returnBtn;
    private ImageButton resetBtn;
    private MeowGame meowGame;
    private Drawable soundYDrawable;
    public static boolean isMusicOn;
    public static boolean isSoundOn;
    private final Stage stage;
    private final OrthographicCamera camera;

    public SettingsUserInterface(OrthographicCamera camera, MeowGame meowGame) {
        this.meowGame = new MeowGame();
        this.camera = camera;

        Drawable resetDrawable = new TextureRegionDrawable(new Texture("UI/btn_reset.png"));
        Drawable returnDrawable = new TextureRegionDrawable(new Texture("UI/btn_return.png"));
        musicSettingBtn = new ImageButton(new TextureRegionDrawable(new Texture("UI/btn_music_on.png")));
        soundYDrawable = new TextureRegionDrawable(new Texture("UI/btn_sound_on.png"));
        soundSettingBtn = new ImageButton(soundYDrawable);
        if (MemoryService.loadIsSoundOn()) {
            soundSettingBtn.getStyle().imageUp = new TextureRegionDrawable(new Texture("UI/btn_sound_on.png"));;
        } else {
            soundSettingBtn.getStyle().imageUp = new TextureRegionDrawable(new Texture("UI/btn_sound_off.png"));
        }
        if (MemoryService.loadIsMusicOn()) {
            musicSettingBtn.getStyle().imageUp = new TextureRegionDrawable(new Texture("UI/btn_music_on.png"));;
        } else {
            musicSettingBtn.getStyle().imageUp = new TextureRegionDrawable(new Texture("UI/btn_music_off.png"));
        }
        returnBtn = new ImageButton(returnDrawable);
        resetBtn = new ImageButton(resetDrawable);
        isSoundOn = MemoryService.loadIsMusicOn();
        isMusicOn = MemoryService.loadIsMusicOn();

        musicSettingBtn.setPosition(
            MeowGame.SCREEN_WIDTH / 2f - 100,
            MeowGame.SCREEN_HEIGHT / 2f + 100
        );
        soundSettingBtn.setPosition(
            MeowGame.SCREEN_WIDTH / 2f - 100,
            MeowGame.SCREEN_HEIGHT / 2f - 50
        );
        returnBtn.setPosition(0, MeowGame.SCREEN_HEIGHT - 50);
        resetBtn.setPosition(MeowGame.SCREEN_WIDTH / 2f - 100, MeowGame.SCREEN_HEIGHT / 2f - 200);

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
                if (MemoryService.loadIsMusicOn()) {
                    musicSettingBtn.getStyle().imageUp = new TextureRegionDrawable(new Texture("UI/btn_music_on.png"));;
                } else {
                    musicSettingBtn.getStyle().imageUp = new TextureRegionDrawable(new Texture("UI/btn_music_off.png"));
                }
                    isMusicOn = MemoryService.loadIsMusicOn();
            }
            if (soundSettingBtn.isPressed()) {
                MemoryService.saveSoundSettings(!MemoryService.loadIsSoundOn());
                if (MemoryService.loadIsSoundOn()) {
                    soundSettingBtn.getStyle().imageUp = new TextureRegionDrawable(new Texture("UI/btn_sound_on.png"));;
                } else {
                    soundSettingBtn.getStyle().imageUp = new TextureRegionDrawable(new Texture("UI/btn_sound_off.png"));
                }
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


