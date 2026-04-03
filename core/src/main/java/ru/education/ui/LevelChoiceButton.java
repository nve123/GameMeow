package ru.education.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import ru.education.MeowGame;

public class LevelChoiceButton {
    private MeowGame meowGame;
    private ImageButton btn;
    private Texture lockTexture;
    private Texture unlockTexture;
    private boolean lock;
    private float x;
    private float y;
    private byte numberLevel;

    public LevelChoiceButton(Texture unlockTexture, float x, float y, byte numberLevel, MeowGame meowGame) {
        this.meowGame = meowGame;
        this.lock = meowGame.getLockedLvls().get(numberLevel);
        this.unlockTexture = unlockTexture;
        this.x = x;
        this.y = y;
        this.numberLevel = numberLevel;
        lockTexture = new Texture("btn_lvl_locked.png");
        Drawable btnDrawable;
        if (lock) {
            btnDrawable = new TextureRegionDrawable(lockTexture);
        } else {
            btnDrawable = new TextureRegionDrawable(unlockTexture);
        }
        btn = new ImageButton(btnDrawable);
        btn.setPosition(x, y);
        btn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                switch (numberLevel) {
                    case 0:
                        if (!lock) meowGame.changeScreen(MeowGame.GAME);
                        break;
                    case 1:
                        if (!lock) meowGame.changeScreen(MeowGame.GAMELVL2);
                        break;
                    case 2:
                        if (!lock) meowGame.changeScreen(MeowGame.GAMELVL3);
                        break;
                }
            }
        });
    }

    public void lockUpdate() {
        this.lock = meowGame.getLockedLvls().get(numberLevel);
    }

    public void addToStage(Stage stage) {
        stage.addActor(btn);
    }

    public void dispose() {
        unlockTexture.dispose();
        lockTexture.dispose();
    }
}
