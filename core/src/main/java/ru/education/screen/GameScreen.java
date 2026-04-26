package ru.education.screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

import ru.education.debug.DebugInfo;
import ru.education.MeowGame;
import ru.education.camera.OrthographicCameraWithLeftRightState;
import ru.education.service.ShopService;
import ru.education.service.WorkerService;
import ru.education.shop.ItemType;
import ru.education.shop.Shop;
import ru.education.tower.Core;
import ru.education.tower.DefensiveTower;
import ru.education.tower.SlotTower;
import ru.education.tower.resource.Resource;
import ru.education.tower.resource.ResourceType;
import ru.education.ui.ChangeLevelUserInterface;
import ru.education.ui.GameUserInterface;
import ru.education.ui.MenuUserInterface;
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

    private Core coreTower;
    private Array<Resource> resourceList;
    private Texture tmpTexture;
    private Vector3 touchPoint;
    private Array<Worker> workers;
    private Array<Worker> activeWorkers;
    private Enemy enemy;
    private BitmapFont font;
    private Array<SlotTower> slotTowerArray;
    private Array<DefensiveTower> defensiveTowerArray;
    private Shop shop;
    private float curTime;
    private DebugInfo debugInfo;
    private ShopService shopService;
    private WorkerService workerService;
    private Array<Rectangle> enemyPathPoint;
    private ChangeLevelUserInterface changeLevelUserInterface;
    private Texture btnlvl2Texture;

    public GameScreen(MeowGame meowGame) {
        this.meowGame = meowGame;
    }

    @Override
    public void show() {
        batch = meowGame.getSpriteBatch();
        camera = new OrthographicCameraWithLeftRightState();
        camera.setToOrtho(false, MeowGame.SCREEN_WIDTH, MeowGame.SCREEN_HEIGHT);
        changeLevelUserInterface = new ChangeLevelUserInterface(meowGame, camera);
        btnlvl2Texture = new Texture("btn_lvl2.png");

        background = new Texture(Gdx.files.internal("game_back.png"));

        gameUserInterface = new GameUserInterface(camera, this);

        Rectangle hitBoxCoreTower = new Rectangle(
            (MeowGame.SCREEN_WIDTH - 170 * 2) + 170 - 30,
            (MeowGame.SCREEN_HEIGHT / 2f - 226 / 4) + 226 / 5,
            25,
            25
        );

        tmpTexture = new Texture(Gdx.files.internal("tmp.png"));
        coreTower = new Core(
            170,
            226,
            MeowGame.SCREEN_WIDTH - 170 * 2,
            MeowGame.SCREEN_HEIGHT / 2f - 226 / 4,
            hitBoxCoreTower
        );

        Resource resourceGold, resourceOre, resourceWood;
        Rectangle workBoxGold = new Rectangle(50 - 25 + 170 - 20, 480 / 2f - 165 / 2f, 15f, 15f);
        resourceGold = new Resource(50 - 25, 480 / 2f - 165 / 2f, ResourceType.GOLD, workBoxGold);
        Rectangle workBoxOre = new Rectangle(50 - 25 + 146 - 20, 480 - 150 - 35, 15f, 15f);
        resourceOre = new Resource(50, 480 - 150 - 35, ResourceType.ORE, workBoxOre);
        Rectangle workBoxWood = new Rectangle(50 - 25 + 150 - 20, 0 + 15, 15f, 15f);
        resourceWood = new Resource(55, 0 + 15, ResourceType.WOOD, workBoxWood);
        resourceList = Array.with(resourceGold, resourceOre, resourceWood);

        workers = new Array<>();
        activeWorkers = new Array<>();
        workers.add(new Worker(coreTower));
        workers.add(new Worker(coreTower));
        workers.add(new Worker(coreTower));
        workers.add(new Worker(coreTower));
        workers.add(new Worker(coreTower));
        workers.add(new Worker(coreTower));

        touchPoint = new Vector3();

        enemyPathPoint = new Array<>();
        enemyPathPoint.add(new Rectangle(MeowGame.SCREEN_WIDTH + 150 + 100 + 50 + 100 + 50, 480 / 2f - 50 - 100, 10, 10));
        enemyPathPoint.add(new Rectangle(MeowGame.SCREEN_WIDTH + 150 + 100 + 50, 480 / 2f + 50, 10, 10));
        enemyPathPoint.add(new Rectangle(MeowGame.SCREEN_WIDTH + 150, 480 / 2f - 50 - 100, 10, 10));

        enemy = new Enemy(
            10,
            coreTower.getHitBox(),
            WORLD_WIDTH - 100,
            MeowGame.SCREEN_HEIGHT / 2f,
            enemyPathPoint
        );

        font = new BitmapFont();

        shop = new Shop(MeowGame.SCREEN_WIDTH + 24, 24);
        shop.addItem(ItemType.TOWER);

        slotTowerArray = new Array<>(6);
        slotTowerArray.add(new SlotTower(MeowGame.SCREEN_WIDTH + 150, 480 / 2f + 50));
        slotTowerArray.add(new SlotTower(MeowGame.SCREEN_WIDTH + 150 + 100 + 50, 480 / 2f + 50));
        slotTowerArray.add(new SlotTower(MeowGame.SCREEN_WIDTH + 150, 480 / 2f - 50 - 100));
        slotTowerArray.add(new SlotTower(MeowGame.SCREEN_WIDTH + 150 + 100 + 50, 480 / 2f - 50 - 100));
        slotTowerArray.add(new SlotTower(MeowGame.SCREEN_WIDTH + 150 + 100 + 50 + 100 + 50, 480 / 2f + 50));
        slotTowerArray.add(new SlotTower(MeowGame.SCREEN_WIDTH + 150 + 100 + 50 + 100 + 50, 480 / 2f - 50 - 100));

        defensiveTowerArray = new Array<>();

        curTime = 0f;

        shopService = new ShopService(slotTowerArray, defensiveTowerArray, shop);
        workerService = new WorkerService(resourceList, workers, activeWorkers);

        debugInfo = new DebugInfo();

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.CLEAR);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        float deltaTime = Gdx.graphics.getDeltaTime();
        curTime += deltaTime;

        if (Gdx.input.justTouched()) {
            camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            debugInfo.addInfo(touchPoint.x + " " + touchPoint.y);

            if (!shop.isActive()) {
                workerService.workerClickProcessing(touchPoint);
                shopService.shopItemClickProcessing(touchPoint);
            } else {
                shopService.shoppingProcess(touchPoint);
                shop.setActive(false);
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
        } else {
            shop.draw(batch);
            for (SlotTower slotTower : slotTowerArray) {
                slotTower.draw(batch);
            }
        }

        for (Worker worker : activeWorkers) {
            if (worker.isAlive()) {
                worker.sleepSametime();
                worker.nextXY();
                if (camera.isLeftState()) {
                    worker.draw(batch);
                }
                worker.setTimeInState(deltaTime);
            }
        }

        if (enemy.isAlive()) {
            enemy.nextXY();
            if (enemy.getX() > MeowGame.SCREEN_WIDTH && !camera.isLeftState()
                || enemy.getX() < MeowGame.SCREEN_WIDTH && camera.isLeftState()) {
                enemy.draw(batch);
            }
            enemy.setTimeInState(deltaTime);
        }

        if (!enemy.isAlive()) {
            User.getInstance().setHp(100);
            User.getInstance().setGold(1000);
            User.getInstance().setOre(2000);
            User.getInstance().setWood(1500);
            meowGame.unlockLevel((byte) 1);
            meowGame.changeScreen(MeowGame.CHANGELVL);
        }

        for (DefensiveTower defensiveTower : defensiveTowerArray) {
            defensiveTower.draw(batch, enemy, curTime);
        }

        debugInfo.draw(batch);

        batch.end();

        workerService.generateWorker(curTime);

        gameUserInterface.drawUI();

        if (User.getInstance().getHp() < 0) {
            User.getInstance().setHp(100);
            User.getInstance().setGold(100);
            User.getInstance().setOre(200);
            User.getInstance().setWood(150);
            meowGame.changeScreen(MeowGame.MENU);
        }
    }

    @Override
    public void dispose() {
        background.dispose();
        gameUserInterface.dispose();
        tmpTexture.dispose();
        font.dispose();

        for (Resource resource : resourceList) {
            resource.dispose();
        }
        coreTower.dispose();

        for (Worker worker : workers) {
            worker.dispose();
        }

        enemy.dispose();

        for (DefensiveTower defensiveTower : defensiveTowerArray) {
            defensiveTower.dispose();
        }

        for (SlotTower slotTower : slotTowerArray) {
            slotTower.dispose();
        }

        shop.dispose();

        debugInfo.dispose();

        User.getInstance().dispose();

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
