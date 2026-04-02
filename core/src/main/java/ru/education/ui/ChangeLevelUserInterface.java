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


    public ChangeLevelUserInterface(MeowGame meowGame, OrthographicCamera camera) {
        this.meowGame = meowGame;
        this.camera = camera;
        btns = new Array<>(3);
        Texture btnlvl1Texture = new Texture("btn_lvl1.png");
        Texture btnlvl2Texture = new Texture("btn_lvl2.png");

       LevelChoiceButton btnlvl1 = new LevelChoiceButton(btnlvl1Texture,
           MeowGame.SCREEN_WIDTH / 2f - btnlvl1Texture.getWidth() / 2f,
           MeowGame.SCREEN_HEIGHT / 2f - btnlvl1Texture.getHeight() / 2f,
           (byte) 0,
           meowGame
       );

       btns.add(btnlvl1);

       LevelChoiceButton btnlvl2 = new LevelChoiceButton(btnlvl2Texture,
           MeowGame.SCREEN_WIDTH / 2f - btnlvl2Texture.getWidth() / 2f + 50,
           MeowGame.SCREEN_HEIGHT / 2f - btnlvl2Texture.getHeight() / 2f,
           (byte) 1,
           meowGame
       );

       btns.add(btnlvl2);

        Viewport fitViewport = new StretchViewport(MeowGame.SCREEN_WIDTH, MeowGame.SCREEN_HEIGHT, camera);
        stage = new Stage(fitViewport);
        Gdx.input.setInputProcessor(stage);

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
    }
}
