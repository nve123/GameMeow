package ru.education.screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import ru.education.debug.DebugInfo;
import ru.education.MeowGame;
import ru.education.camera.OrthographicCameraWithLeftRightState;
import ru.education.fontBuilder.FontBuilder;
import ru.education.service.ShopService;
import ru.education.service.TimerService;
import ru.education.service.WorkerService;
import ru.education.shop.ItemType;
import ru.education.shop.Shop;
import ru.education.tower.Core;
import ru.education.tower.DefensiveTower;
import ru.education.tower.SlotTower;
import ru.education.tower.resource.Resource;
import ru.education.tower.resource.ResourceType;
import ru.education.tutorial.Tutorial;
import ru.education.ui.ChangeLevelUserInterface;
import ru.education.ui.GameUserInterface;
import ru.education.ui.SettingsUserInterface;
import ru.education.unit.Enemy;
import ru.education.unit.Worker;
import ru.education.user.User;

public class GameScreenLvl1 implements Screen {
    public static final int WORLD_WIDTH = MeowGame.SCREEN_WIDTH * 2;
    private final MeowGame meowGame;
    private OrthographicCameraWithLeftRightState camera;
    private SpriteBatch batch;
    private Texture background;
    private GameUserInterface gameUserInterface;
    private TimerService timer;
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
    //private DebugInfo debugInfo;
    private ShopService shopService;
    private WorkerService workerService;
    private Array<Rectangle> enemyPathPoint;
    private ChangeLevelUserInterface changeLevelUserInterface;
    private TextureAtlas atlasStay;
    private TextureAtlas atlasGoTo;
    private TextureAtlas atlasAttack;
    private Tutorial tutorial;
    private Stage stage;


    public GameScreenLvl1(MeowGame meowGame) {
        this.meowGame = meowGame;
    }

    @Override
    public void show() {
        batch = meowGame.getSpriteBatch();
        camera = new OrthographicCameraWithLeftRightState();
        camera.setToOrtho(false, MeowGame.SCREEN_WIDTH, MeowGame.SCREEN_HEIGHT);
        changeLevelUserInterface = new ChangeLevelUserInterface(meowGame, camera);
        Viewport fitViewport = new StretchViewport(MeowGame.SCREEN_WIDTH, MeowGame.SCREEN_HEIGHT, camera);
        stage = new Stage(fitViewport);
        Gdx.input.setInputProcessor(stage);

        background = new Texture(Gdx.files.internal("backgrounds/game_back_lvl1.png"));

        gameUserInterface = new GameUserInterface(camera, this, meowGame, stage);

        Rectangle hitBoxCoreTower = new Rectangle(
            (MeowGame.SCREEN_WIDTH - 170 * 2) + 170 - 30,
            (MeowGame.SCREEN_HEIGHT / 2f - 226 / 4) + 226 / 5,
            25,
            25
        );

        tmpTexture = new Texture(Gdx.files.internal("debug/tmp.png"));
        coreTower = new Core(
            170,
            226,
            MeowGame.SCREEN_WIDTH - 170 * 2,
            MeowGame.SCREEN_HEIGHT / 2f - 226 / 4,
            hitBoxCoreTower
        );

        timer = new TimerService();

        Resource resourceGold, resourceOre, resourceWood;
        Rectangle workBoxGold = new Rectangle(50 - 25 + 170 - 20 - 50, 480 / 2f - 165 / 2f, 15f, 15f);
        resourceGold = new Resource(50, 480 / 2f - 165 / 2f, ResourceType.GOLD, workBoxGold);
        Rectangle workBoxOre = new Rectangle(50 - 25 + 146 - 20 - 10 - 10, 480 - 150 - 35, 15f, 15f);
        resourceOre = new Resource(50, 480 - 150 - 35, ResourceType.ORE, workBoxOre);
        Rectangle workBoxWood = new Rectangle(50 - 25 + 150 - 20 - 20, 0 + 15 + 15, 15f, 15f);
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
        enemyPathPoint.add(new Rectangle(MeowGame.SCREEN_WIDTH - 170 * 2, MeowGame.SCREEN_HEIGHT / 2f - 226 / 4 + 40, 10, 10));

        enemy = new Enemy(
            10,
            coreTower.getHitBox(),
            WORLD_WIDTH - 100,
            MeowGame.SCREEN_HEIGHT / 2f - 226 / 4 + 40,
            enemyPathPoint,
            atlasStay = new TextureAtlas("animations/slime_stay.atlas"),
            atlasGoTo = new TextureAtlas("animations/slime_walk.atlas"),
            atlasAttack = new TextureAtlas("animations/slime_attack.atlas")
        );


        timer.setActive(true);

        font = FontBuilder.generate(8, Color.BLACK, "fonts/Curtsweeper-Regular.otf");

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

        tutorial = new Tutorial(
            50,
            50,
            "   1. Tap cat to knock it down.\n" +
                "\n" +
                "   2. Tap sleeping cat, send it to a \n" +
                "  resource (gold, ore, wood).\n" +
                "\n" +
                "   3. Gather resources to buy defensive \n" +
                "  towers.\n" +
                "\n" +
                "   4. Go to battle screen: arrow in the \n" +
                "  right corner.\n" +
                "\n" +
                "   5. Buy towers in the shop at the  \n" +
                "  bottom, place in empty slot.\n" +
                "\n" +
                "      Tap to close this message                      ",
            380,
            700,
            new Texture("backgrounds/change_back.png"),
            camera,
            stage
        );

        curTime = 0f;

        shopService = new ShopService(slotTowerArray, defensiveTowerArray, shop);
        workerService = new WorkerService(resourceList, workers, activeWorkers);

        //debugInfo = new DebugInfo();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.CLEAR);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        float deltaTime = Gdx.graphics.getDeltaTime();
        curTime += deltaTime;


        batch.begin();

        batch.draw(background, 0, 0, MeowGame.SCREEN_WIDTH * 2, MeowGame.SCREEN_HEIGHT);


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

        if (!enemy.isAlive()) {
            for (Worker worker : workers) {
                worker.stopSound();
            }
            User.getInstance().setHp(100);
            User.getInstance().setGold(0);
            User.getInstance().setOre(0);
            User.getInstance().setWood(0);
            meowGame.unlockLevel((byte) 1);
            /*MemoryService.saveUnlocks(meowGame.getLockedLvls());*/
            meowGame.changeScreen(MeowGame.CHANGELVL);
        }

        for (DefensiveTower defensiveTower : defensiveTowerArray) {
            defensiveTower.draw(batch, enemy, curTime);
        }

        if (camera.isLeftState()) {
            coreTower.draw(batch);
            for (Resource resource : resourceList) {
                resource.draw(batch);
            }
            font.draw(
                batch,
                User.getInstance().fullInfo(),
                coreTower.getX(),
                coreTower.getY()
            );
        } else {
            shop.draw(batch);
            for (SlotTower slotTower : slotTowerArray) {
                slotTower.draw(batch);
            }
        }

        if (Gdx.input.justTouched()) {
            camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            //debugInfo.addInfo(touchPoint.x + " " + touchPoint.y);

            if (!shop.isActive()) {
                workerService.workerClickProcessing(touchPoint);
                shopService.shopItemClickProcessing(touchPoint);
            } else {
                shopService.shoppingProcess(touchPoint);
                shop.setActive(false);
            }
        }
        if (enemy.isAlive()) {
            enemy.nextXY();
            if (enemy.getX() > MeowGame.SCREEN_WIDTH && !camera.isLeftState()
                || enemy.getX() < MeowGame.SCREEN_WIDTH && camera.isLeftState()) {
                enemy.draw(batch);
            }
            if (!tutorial.isTutorialOn()) {
                enemy.setTimeInState(deltaTime);
                timer.tick(deltaTime);
            }
        }
        if (!tutorial.isTutorialOn()) workerService.generateWorker(curTime);

        timer.draw(batch, font, 1400, 20);

        batch.end();

        gameUserInterface.drawUI();

        if (User.getInstance().getHp() < 0) {
            User.getInstance().setHp(100);
            User.getInstance().setGold(0);
            User.getInstance().setOre(0);
            User.getInstance().setWood(0);
            meowGame.changeScreen(MeowGame.MENU);
        }
    }

    @Override
    public void dispose() {
        for (Worker worker : workers) {
            worker.stopSound();
            worker.dispose();
        }
        background.dispose();
        tutorial.dispose();
        gameUserInterface.dispose();
        tmpTexture.dispose();
        font.dispose();
        atlasAttack.dispose();
        atlasGoTo.dispose();
        atlasStay.dispose();

        for (Resource resource : resourceList) {
            resource.dispose();
        }
        coreTower.dispose();


        enemy.dispose();

        for (DefensiveTower defensiveTower : defensiveTowerArray) {
            defensiveTower.dispose();
        }

        for (SlotTower slotTower : slotTowerArray) {
            slotTower.dispose();
        }

        shop.dispose();

        //debugInfo.dispose();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        for (Worker worker : workers) {
            worker.stopSound();
        }
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        for (Worker worker : workers) {
            worker.stopSound();
        }
    }
}
