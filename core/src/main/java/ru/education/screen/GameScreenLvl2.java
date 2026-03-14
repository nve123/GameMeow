package ru.education.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import ru.education.ui.GameUserInterface;
import ru.education.ui.MenuUserInterface;
import ru.education.unit.Enemy;
import ru.education.unit.Worker;
import ru.education.user.User;
import ru.education.unit.Worker;

public class GameScreenLvl2 implements Screen {
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
    private Array<Enemy> enemies;
    private BitmapFont font;
    private Array<SlotTower> slotTowerArray;
    private Array<DefensiveTower> defensiveTowerArray;
    private Shop shop;
    private Shop shop2;
    private float curTime;
    private DebugInfo debugInfo;
    private int plusHP = 0;
    private ShopService shopService;
    private WorkerService workerService;
    private Array<Rectangle> enemyPathPoint;
    private ShopService shopService2;
    private Enemy enemy;

    public GameScreenLvl2(MeowGame meowGame) {
        this.meowGame = meowGame;
    }

    @Override
    public void show() {
        User.getInstance().setHp(100);
        batch = meowGame.getSpriteBatch();

        camera = new OrthographicCameraWithLeftRightState();
        camera.setToOrtho(false, MeowGame.SCREEN_WIDTH, MeowGame.SCREEN_HEIGHT);

        background = new Texture(Gdx.files.internal("game_back_lvl_2.png"));

        gameUserInterface = new GameUserInterface(camera, this);

        tmpTexture = new Texture(Gdx.files.internal("tmp.png"));
        coreTower = new Core(
            170,
            226,
            20,
            140
        );

        Resource resourceGold, resourceOre, resourceWood;
        resourceGold = new Resource(20, 1, ResourceType.GOLD);
        resourceOre = new Resource(200, 480 - 420, ResourceType.ORE);
        resourceWood = new Resource(300, 480 - 322, ResourceType.WOOD);
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
        enemyPathPoint.add(new Rectangle(840 - 5, 480 - 29 - 5, 10, 10));
        enemyPathPoint.add(new Rectangle(840 - 5, 480 - 214 - 5, 10, 10));
        enemyPathPoint.add(new Rectangle(1554 - 5, 480 - 214 - 5, 10, 10));
        enemyPathPoint.add(new Rectangle(1554 - 5, 480 - 417 - 5, 10, 10));
        enemyPathPoint.add(new Rectangle(695 - 5, 480 - 417 - 5, 10, 10));
        enemyPathPoint.add(new Rectangle(695 - 5, 480 - 44 - 5, 10, 10));
        enemyPathPoint.add(new Rectangle(132 - 5, 480 - 44 - 5, 10, 10));

        //enemies = new Array<>();
        enemy = new Enemy(
            10,
            coreTower.getHitBox(),
            1554,
            417,
            enemyPathPoint
        );

        font = new BitmapFont();

        shop = new Shop(MeowGame.SCREEN_WIDTH + 24, 24);
        shop2 = new Shop(24 + 300, 24);
        shop.addItem(ItemType.TOWER);
        shop2.addItem(ItemType.TOWER);
        shop.addItem(ItemType.UPDATE_DMG);
        shop2.addItem(ItemType.UPDATE_DMG);
        shop.addItem(ItemType.UPDATE_SPEED);
        shop2.addItem(ItemType.UPDATE_SPEED);

        slotTowerArray = new Array<>(6);
        slotTowerArray.add(new SlotTower(604, 480 - 408));
        slotTowerArray.add(new SlotTower(604, 480 - 170));
        slotTowerArray.add(new SlotTower(153, 480 - 147));
        slotTowerArray.add(new SlotTower(50, 480 - 147));
        slotTowerArray.add(new SlotTower(MeowGame.SCREEN_WIDTH + 84, 480 - 177));
        slotTowerArray.add(new SlotTower(MeowGame.SCREEN_WIDTH + 104, 480 - 391));
        slotTowerArray.add(new SlotTower(1171, 480 - 177));
        slotTowerArray.add(new SlotTower(1171, 480 - 391));
        slotTowerArray.add(new SlotTower(1413, 480 - 177));
        slotTowerArray.add(new SlotTower(1413, 480 - 391));

        defensiveTowerArray = new Array<>();

        curTime = 0f;

        shopService = new ShopService(slotTowerArray, defensiveTowerArray, shop);
        shopService2 = new ShopService(slotTowerArray, defensiveTowerArray, shop2);
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
            if (!shop2.isActive()) {
                workerService.workerClickProcessing(touchPoint);
                shopService2.shopItemClickProcessing(touchPoint);
            } else {
                shopService2.shoppingProcess(touchPoint);
                shop2.setActive(false);
            }
        }

        batch.begin();

        batch.draw(background, 0, 0, MeowGame.SCREEN_WIDTH * 2, MeowGame.SCREEN_HEIGHT);

        if (camera.isLeftState()) {
            coreTower.draw(batch);
            shop2.draw(batch);
            for (SlotTower slotTower : slotTowerArray) {
                slotTower.draw(batch);
            }
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
        if (!enemy.isAlive() && plusHP < 139) {
            plusHP += 10;
        }

        //if (!enemy.isAlive()) {
        //    meowGame.changeScreen(MeowGame.GAMELVL2);
        //}

        for (DefensiveTower defensiveTower : defensiveTowerArray) {
            defensiveTower.draw(batch, enemy, curTime);
        }

        debugInfo.draw(batch);

        batch.end();

        workerService.generateWorker(curTime);

        gameUserInterface.drawUI();

        if (User.getInstance().getHp() < 0) {
            meowGame.changeScreen(MeowGame.MENU);
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
        shop2.dispose();

        debugInfo.dispose();
    }
}
