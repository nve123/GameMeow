package ru.education.screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import ru.education.MeowGame;
import ru.education.camera.OrthographicCameraWithLeftRightState;
import ru.education.tower.CoreTower;
import ru.education.tower.resource.Resource;
import ru.education.tower.resource.ResourceType;
import ru.education.ui.GameUserInterface;
import ru.education.unit.Enemy;
import ru.education.unit.Worker;
import ru.education.user.User;

public class GameScreen implements Screen {
    public static final int WORLD_WIDTH = MeowGame.SCREEN_WIDTH * 2;
    private final MeowGame meowGame;
    private OrthographicCameraWithLeftRightState camera;
    private SpriteBatch batch;
    private Texture background;
    private GameUserInterface gameUserInterface;

    private CoreTower coreTower;
    private Array<Resource> resourceList;
    private Texture tmpTexture;
    private Vector3 touchPoint;
    private Worker worker;
    private Enemy enemy;
    private BitmapFont font;

    public GameScreen(MeowGame meowGame) {
        this.meowGame = meowGame;
    }

    @Override
    public void show() {
        batch = meowGame.getSpriteBatch();

        camera = new OrthographicCameraWithLeftRightState();
        camera.setToOrtho(false, MeowGame.SCREEN_WIDTH, MeowGame.SCREEN_HEIGHT);

        background = new Texture(Gdx.files.internal("game_back.png"));

        gameUserInterface = new GameUserInterface(camera);

        tmpTexture = new Texture(Gdx.files.internal("tmp.png"));
        coreTower = new CoreTower(
            170,
            226
        );

        Resource resourceGold, resourceOre, resourceWood;
        resourceGold = new Resource(
            50 - 25,
            480 / 2f - 165 / 2f,
            new Texture(Gdx.files.internal("gold.png")),
            ResourceType.GOLD, 170, 119);
        resourceOre = new Resource(
            50,
            480 - 150 - 35,
            new Texture(Gdx.files.internal("ore.png")),
            ResourceType.ORE, 146, 119);
        resourceWood = new Resource(
            55,
            0 + 15,
            new Texture(Gdx.files.internal("wood.png")),
            ResourceType.WOOD, 150, 125);
        resourceList = Array.with(resourceGold, resourceOre, resourceWood);

        worker = new Worker(coreTower);
        touchPoint = new Vector3();

        enemy = new Enemy(
            10,
            coreTower.getHitBox(),
            WORLD_WIDTH - 100,
            MeowGame.SCREEN_HEIGHT / 2f
        );

        font = new BitmapFont();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.CLEAR);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        float deltaTime = Gdx.graphics.getDeltaTime();

        if (Gdx.input.justTouched()) {
            camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (worker.contains(touchPoint.x, touchPoint.y)) {
                worker.clicked();
            } else {
                if (worker.getCurrentState() == Worker.StateWorker.CLICKED) {
                    boolean startWorking = false;

                    for (Resource resource : resourceList) {

                        if (resource.contains(touchPoint.x, touchPoint.y)) {
                            worker.setWorkingPlace(resource);
                            worker.setCurrentState(Worker.StateWorker.GO_TO);
                            worker.setDestination(resource.getWorkBox());
                            startWorking = true;
                        }
                    }

                    if (!startWorking) worker.setCurrentState(Worker.StateWorker.SLEEP);
                }
            }


        }

        batch.begin();

        batch.draw(background, 0, 0, MeowGame.SCREEN_WIDTH * 2, MeowGame.SCREEN_HEIGHT);

        if (camera.isLeftState()) {
            coreTower.draw(batch);
            for (Resource resource : resourceList) {
                resource.draw(batch);
            }
            font.draw(
                batch,
                User.getInstance().fullInfo(),
                MeowGame.SCREEN_WIDTH - coreTower.getTexture().getWidth() * 2,
                coreTower.getY()
            );
        }

        if (worker.isAlive()) {
            worker.sleepSametime();
            worker.nextXY();
            if (camera.isLeftState()) {
                worker.draw(batch);
            }
            worker.setTimeInState(deltaTime);
        }

        if (enemy.isAlive()) {
            enemy.nextXY();
            if (enemy.getX() > MeowGame.SCREEN_WIDTH && !camera.isLeftState()
                || enemy.getX() < MeowGame.SCREEN_WIDTH && camera.isLeftState()) {
                enemy.draw(batch);
            }
            enemy.setTimeInState(deltaTime);
        }

        batch.end();

        gameUserInterface.drawUI();
    }

    @Override
    public void dispose() {
        background.dispose();
        gameUserInterface.dispose();
        tmpTexture.dispose();

        for (Resource resource : resourceList) {
            resource.dispose();
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
