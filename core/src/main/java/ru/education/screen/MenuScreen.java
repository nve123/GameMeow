package ru.education.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;

import ru.education.MeowGame;
import ru.education.camera.OrthographicCameraWithLeftRightState;
import ru.education.ui.MenuUserInterface;
import ru.education.util.AnimationUtil;

public class MenuScreen implements Screen {
    private final MeowGame meowGame;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Texture background;
    private MenuUserInterface menuUserInterface;
    private Animation<TextureRegion> cat, enemy;
    private float curTime;

    public MenuScreen(MeowGame meowGame) {
        this.meowGame = meowGame;
        curTime = 0;
    }

    @Override
    public void show() {
        batch = meowGame.getSpriteBatch();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, MeowGame.SCREEN_WIDTH, MeowGame.SCREEN_HEIGHT);

        background = new Texture(Gdx.files.internal("menu_back.png"));

        menuUserInterface = new MenuUserInterface(
            camera,
            () -> meowGame.changeScreen(MeowGame.GAME)
        );

        initAnimation();
    }

    private void initAnimation() {
        enemy = AnimationUtil.getAnimationFromAtlas(
            "enemyatack.atlas",
            4f
        );
        cat = AnimationUtil.getAnimationFromAtlas(
            "catflysupersmall.atlas",
            4f
        );
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.CLEAR);

        float dTime = Gdx.graphics.getDeltaTime();
        curTime += dTime;

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        batch.draw(background, 0, 0, MeowGame.SCREEN_WIDTH, MeowGame.SCREEN_HEIGHT);

        batch.draw(
            cat.getKeyFrame(curTime, true),
            100,
            100,
            80f,
            200f
        );

        batch.draw(
            enemy.getKeyFrame(curTime, true),
            MeowGame.SCREEN_WIDTH - 220,
            100,
            120f,
            120f
        );

        batch.end();

        menuUserInterface.drawUI();
    }

    @Override
    public void dispose() {
        background.dispose();
        menuUserInterface.dispose();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }
}
