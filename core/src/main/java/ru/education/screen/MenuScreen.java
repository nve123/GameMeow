package ru.education.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

import ru.education.MeowGame;
import ru.education.ui.MenuUserInterface;
import ru.education.util.AnimationUtil;

public class MenuScreen implements Screen {
    private final MeowGame meowGame;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Texture background;
    private MenuUserInterface menuUserInterface;
    private Animation<TextureRegion> cat, enemy;
    protected Array<TextureAtlas> textureAtlasArray;
    private float curTime;

    public MenuScreen(MeowGame meowGame) {
        this.meowGame = meowGame;
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
        textureAtlasArray = new Array<>();

        TextureAtlas atlas = new TextureAtlas("enemyatack.atlas");
        enemy = AnimationUtil.getAnimationFromAtlas(
            atlas,
            4f
        );
        textureAtlasArray.add(atlas);

        atlas = new TextureAtlas("catflysupersmall.atlas");
        cat = AnimationUtil.getAnimationFromAtlas(
            atlas,
            4f
        );
        textureAtlasArray.add(atlas);

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.CLEAR);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        float deltaTime = Gdx.graphics.getDeltaTime();
        curTime += deltaTime;

        batch.begin();

        batch.draw(background, 0, 0, MeowGame.SCREEN_WIDTH, MeowGame.SCREEN_HEIGHT);
        /*meowGame.getFont().draw(
                batch,
                "TAP TO START",
                MeowGame.SCREEN_WIDTH / 2f - 100,
                MeowGame.SCREEN_HEIGHT / 2f
        );*/
        batch.draw(
            cat.getKeyFrame(curTime, true),
            0 + 100,
            0 + 100,
            80,
            200
        );
        batch.draw(
            enemy.getKeyFrame(curTime, true),
            MeowGame.SCREEN_WIDTH - 120 - 100,
            0 + 100,
            120,
            120
        );

        batch.end();

        menuUserInterface.drawUI();
    }

    @Override
    public void dispose() {
        background.dispose();
        menuUserInterface.dispose();
        for (TextureAtlas atlas : textureAtlasArray) {
            atlas.dispose();
        }
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
