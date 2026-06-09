package ru.education.tutorial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import ru.education.MeowGame;
import ru.education.camera.OrthographicCameraWithLeftRightState;
import ru.education.fontBuilder.FontBuilder;

public class Tutorial {
    private float x;
    private float y;
    private String text;
    private float height;
    private float wight;
    private BitmapFont font;
    private Label label;
    private LabelStyle style;
    private Stage stage;
    private final OrthographicCamera camera;
    private boolean isTutorialOn;

    public Tutorial(float x, float y, String text, float height, float wight, Texture texture, OrthographicCameraWithLeftRightState camera, Stage stage) {
        this.x = x;
        this.y = y;
        this.text = text;
        this.camera = camera;
        this.height = height;
        this.wight = wight;
        this.stage = stage;
        isTutorialOn = true;
        font = FontBuilder.generate(8, Color.BLACK, "fonts/Curtsweeper-Regular.otf");
        style = new LabelStyle();
        style.font = font;
        style.fontColor = Color.WHITE;
        style.background = new TextureRegionDrawable(new TextureRegion(texture));
        label = new Label(text, style);
        label.setPosition(x, y);
        label.setSize(wight, height);
        label.setAlignment(Align.left);
        label.setWrap(true);
        label.setFontScale(2.0f);

        //label.setTouchable(Touchable.disabled);

        label.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                label.remove();
                isTutorialOn = false;
            }
        });
        stage.addActor(label);
    }

    public void draw() {
        stage.act();
        stage.draw();
    }

    public boolean isTutorialOn() {
        return isTutorialOn;
    }

    public void dispose() {
        stage.dispose();
    }
}
