package ru.education.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import ru.education.MeowGame;

public class ChangeLevelUserInterface {
    private final MeowGame meowGame;
    private final Array<LevelChoiceButton> btns;
    private final Stage stage;
    private final OrthographicCamera camera;
    private ImageButton returnBtn;


    public ChangeLevelUserInterface(MeowGame meowGame, OrthographicCamera camera) {
        this.meowGame = meowGame;
        this.camera = camera;
        btns = new Array<>(3);
        Texture btnlvl1Texture = new Texture("UI/btn_lvl1.png");
        Texture btnlvl2Texture = new Texture("UI/btn_lvl2.png");
        Texture btnlvl3Texture = new Texture("UI/btn_lvl3.png");
        Texture btnlvl4Texture = new Texture("UI/btn_lvl4.png");
        Texture btnlvl5Texture = new Texture("UI/btn_lvl5.png");
        Drawable returnDrawable = new TextureRegionDrawable(new Texture("UI/btn_return.png"));
        returnBtn = new ImageButton(returnDrawable);

       LevelChoiceButton btnlvl1 = new LevelChoiceButton
           (btnlvl1Texture,
           50,
           480 - 260,
           (byte) 0,
           meowGame
       );

       btns.add(btnlvl1);

       LevelChoiceButton btnlvl2 = new LevelChoiceButton(
           btnlvl2Texture,
           242,
           480 - 270,
           (byte) 1,
           meowGame
       );

       btns.add(btnlvl2);

        LevelChoiceButton btnlvl3 = new LevelChoiceButton(
            btnlvl3Texture,
            376,
            480 - 270 - 10  ,
            (byte) 2,
            meowGame
        );
        LevelChoiceButton btnlvl4 = new LevelChoiceButton(
            btnlvl4Texture,
            540,
            480 - 257,
            (byte) 3,
            meowGame
        );
        btns.add(btnlvl4);
        LevelChoiceButton btnlvl5 = new LevelChoiceButton(
            btnlvl5Texture,
            710,
            480 - 285,
            (byte) 4,
            meowGame
        );
        btns.add(btnlvl5);
        btns.add(btnlvl3);

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

        stage.addActor(returnBtn);

        for (LevelChoiceButton btn : btns) {
            btn.addToStage(stage);
        }
    }

    public void drawUI() {
        stage.act();
        stage.draw();
    }

    public void updateButtonsState(){
        for (LevelChoiceButton btn : btns) {
            btn.lockUpdate();
        }
    }

    public void dispose() {
        stage.dispose();
        for (LevelChoiceButton btn : btns) {
            btn.dispose();
        }
    }
}
