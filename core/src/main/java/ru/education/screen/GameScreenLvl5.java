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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

import ru.education.MeowGame;
import ru.education.camera.OrthographicCameraWithLeftRightState;
import ru.education.debug.DebugInfo;
import ru.education.fontBuilder.FontBuilder;
import ru.education.service.ShopService;
import ru.education.service.TimerService;
import ru.education.service.WaveService;
import ru.education.service.WorkerService;
import ru.education.shop.ItemType;
import ru.education.shop.Shop;
import ru.education.tower.Core;
import ru.education.tower.DefensiveTower;
import ru.education.tower.SlotTower;
import ru.education.tower.resource.Resource;
import ru.education.tower.resource.ResourceType;
import ru.education.ui.GameUserInterface;
import ru.education.ui.SettingsUserInterface;
import ru.education.unit.Enemy;
import ru.education.unit.Worker;
import ru.education.user.User;
import ru.education.wave.Wave;

public class GameScreenLvl5 implements Screen {
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
    private Array<Wave> waves;
    private BitmapFont font;
    private Array<SlotTower> slotTowerArray;
    private Array<DefensiveTower> defensiveTowerArray;
    private Shop shop;
    //private Shop shop2;
    private float curTime;
    //private DebugInfo debugInfo;
    private ShopService shopService;
    private WorkerService workerService;
    private Array<Rectangle> enemyPathPoint1;
    private Array<Rectangle> enemyPathPoint2;
    //private ShopService shopService2;
    private WaveService waveService;
    //private Enemy enemy;
    TextureAtlas atlasAttack;
    TextureAtlas atlasGoTo;
    TextureAtlas atlasStay;
    TextureAtlas atlasFly;
    TextureAtlas atlasFall;
    TextureAtlas atlasSleep;
    TextureAtlas atlasClicked;
    TextureAtlas atlasWalk;
    TextureAtlas atlasWork;
    TextureAtlas atlasGoFrom;
    TextureAtlas atlasStay2;
    TextureAtlas atlasGoTo2;
    TextureAtlas atlasAttack2;

    public GameScreenLvl5(MeowGame meowGame) {
        this.meowGame = meowGame;
    }

    @Override
    public void show() {
        User.getInstance().setHp(100);
        User.getInstance().setGold(0);
        User.getInstance().setOre(0);
        User.getInstance().setWood(0);
        batch = meowGame.getSpriteBatch();

        camera = new OrthographicCameraWithLeftRightState();
        camera.setToOrtho(false, MeowGame.SCREEN_WIDTH, MeowGame.SCREEN_HEIGHT);

        background = new Texture(Gdx.files.internal("backgrounds/game_back_lvl5.png"));


        gameUserInterface = new GameUserInterface(camera, this, meowGame);

        Rectangle hitBoxCoreTower = new Rectangle(
            190 + 200 + 120,
            480 - 260,
            25,
            25
        );

        tmpTexture = new Texture(Gdx.files.internal("debug/tmp.png"));
        coreTower = new Core(
            170,
            226,
            190 + 175,
            480 - 300,
            hitBoxCoreTower
        );

        Resource resourceGold, resourceOre, resourceWood;
        Rectangle workBoxGold = new Rectangle(130 - 25 + 170 - 20 - 50, 0 + 20, 15f, 15f);
        resourceGold = new Resource(130, 0 + 20, ResourceType.GOLD, workBoxGold);
        Rectangle workBoxOre = new Rectangle(130 - 25 + 146 - 20 - 10 - 10, 480 - 120, 15f, 15f);
        resourceOre = new Resource(130, 480 - 120, ResourceType.ORE, workBoxOre);
        Rectangle workBoxWood = new Rectangle(34 - 25 + 150 - 20 - 20, 480 - 300, 15f, 15f);
        resourceWood = new Resource(34, 480 - 300 - 15, ResourceType.WOOD, workBoxWood);
        resourceList = Array.with(resourceGold, resourceOre, resourceWood);

        workers = new Array<>();
        activeWorkers = new Array<>();

        atlasFly = new TextureAtlas("animations/hero_cat_fly.atlas");
        atlasFall = new TextureAtlas("animations/hero_cat_fall.atlas");
        atlasSleep = new TextureAtlas("animations/hero_cat_sleep.atlas");
        atlasClicked = new TextureAtlas("animations/hero_cat_shine.atlas");
        atlasWalk = new TextureAtlas("animations/hero_cat_walk.atlas");
        atlasWork = new TextureAtlas("animations/hero_cat_work.atlas");
        atlasGoFrom = new TextureAtlas("animations/hero_cat_go_to.atlas");

        workers.add(new Worker(coreTower, atlasFly, atlasFall, atlasSleep, atlasClicked, atlasWalk, atlasWork, atlasGoFrom));
        workers.add(new Worker(coreTower, atlasFly, atlasFall, atlasSleep, atlasClicked, atlasWalk, atlasWork, atlasGoFrom));
        workers.add(new Worker(coreTower, atlasFly, atlasFall, atlasSleep, atlasClicked, atlasWalk, atlasWork, atlasGoFrom));
        workers.add(new Worker(coreTower, atlasFly, atlasFall, atlasSleep, atlasClicked, atlasWalk, atlasWork, atlasGoFrom));
        workers.add(new Worker(coreTower, atlasFly, atlasFall, atlasSleep, atlasClicked, atlasWalk, atlasWork, atlasGoFrom));
        workers.add(new Worker(coreTower, atlasFly, atlasFall, atlasSleep, atlasClicked, atlasWalk, atlasWork, atlasGoFrom));

        touchPoint = new Vector3();

        enemyPathPoint1 = new Array<>();
        enemyPathPoint1.add(new Rectangle(1180 - 10 - 20, 0 + 10, 10, 10));
        enemyPathPoint1.add(new Rectangle(950 , 480 - 260- 10, 10, 10));
        enemyPathPoint2 = new Array<>();
        enemyPathPoint2.add(new Rectangle(1180, 480 - 10, 10, 10));
        enemyPathPoint2.add(new Rectangle(930 , 480 - 260 - 10, 10, 10));

        Array<Enemy> enemiesWave0 = new Array<>();
        Array<Enemy> enemiesWave1 = new Array<>();
        Array<Enemy> enemiesWave2 = new Array<>();
        Array<Enemy> enemiesWave3 = new Array<>();

        atlasStay2 = new TextureAtlas("animations/slime_stay.atlas");
        atlasGoTo2 = new TextureAtlas("animations/slime_walk.atlas");
        atlasAttack2 = new TextureAtlas("animations/slime_attack.atlas");

        enemiesWave0.add(new Enemy(10, coreTower.getHitBox(), 1600, 480 + 50, enemyPathPoint1, atlasStay2, atlasGoTo2, atlasAttack2));
        enemiesWave0.add(new Enemy(10, coreTower.getHitBox(), 1600, 0 - 30 , enemyPathPoint2, atlasStay2, atlasGoTo2, atlasAttack2));

        enemiesWave1.add(new Enemy(10, coreTower.getHitBox(), 1600, 0, enemyPathPoint2, atlasStay2, atlasGoTo2, atlasAttack2));
        enemiesWave1.add(new Enemy(10, coreTower.getHitBox(), 1650, 480, enemyPathPoint1, atlasStay2, atlasGoTo2, atlasAttack2));
        enemiesWave1.add(new Enemy(10, coreTower.getHitBox(), 1650, - 50, enemyPathPoint2, atlasStay2, atlasGoTo2, atlasAttack2));

        enemiesWave2.add(new Enemy(10, coreTower.getHitBox(), 1600, 0, enemyPathPoint2, atlasStay2, atlasGoTo2, atlasAttack2));
        enemiesWave2.add(new Enemy(10, coreTower.getHitBox(), 1650, - 50, enemyPathPoint2, atlasStay2, atlasGoTo2, atlasAttack2));
        enemiesWave2.add(new Enemy(10, coreTower.getHitBox(), 1600, 480, enemyPathPoint1, atlasStay2, atlasGoTo2, atlasAttack2));
        enemiesWave2.add(new Enemy(10, coreTower.getHitBox(), 1650, 480 + 50, enemyPathPoint1, atlasStay2, atlasGoTo2, atlasAttack2));
        enemiesWave2.add(new Enemy(10, coreTower.getHitBox(), 1700, - 100, enemyPathPoint2, atlasStay2, atlasGoTo2, atlasAttack2));

        enemiesWave3.add(new Enemy(
                200,
                coreTower.getHitBox(),
                1600,
                0 - 50,
                enemyPathPoint2
            )
        );
        timer = new TimerService();
        timer.setActive(true);

        Wave wave0 = new Wave(enemiesWave0);
        Wave wave1 = new Wave(enemiesWave1);
        Wave wave2 = new Wave(enemiesWave2);
        Wave wave3 = new Wave(enemiesWave3);

        waves = new Array<>(4);
        waves.add(wave0, wave1, wave2, wave3);

        waveService = new WaveService(waves);


        font = FontBuilder.generate(10, Color.WHITE, "fonts/Curtsweeper-Regular.otf", true);

        shop = new Shop(MeowGame.SCREEN_WIDTH + 24, 24);
        shop.addItem(ItemType.TOWER);
        shop.addItem(ItemType.UPDATE_DMG);
        shop.addItem(ItemType.UPDATE_SPEED);

        slotTowerArray = new Array<>(6);
        slotTowerArray.add(new SlotTower(830, 480 - 420));
        slotTowerArray.add(new SlotTower(830, 480 - 205));
        slotTowerArray.add(new SlotTower(1110 + 30, 480 - 290));
        slotTowerArray.add(new SlotTower(1350, 480 - 140));
        slotTowerArray.add(new SlotTower(1350, 480 - 450));
        slotTowerArray.add(new SlotTower(1500, 480 - 320));

        defensiveTowerArray = new Array<>();

        curTime = 0f;

        shopService = new ShopService(slotTowerArray, defensiveTowerArray, shop);
        //shopService2 = new ShopService(slotTowerArray, defensiveTowerArray, shop2);
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
        for (Enemy enemy : waveService.getCurWave().getEnemies()) {
            for (DefensiveTower defensiveTower : defensiveTowerArray) {
                defensiveTower.draw(batch, enemy, curTime);
            }
        }

        if (camera.isLeftState()) {
            coreTower.draw(batch);
            //shop2.draw(batch);
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
                coreTower.getY() - 10
            );
        } else {
            shop.draw(batch);
            for (SlotTower slotTower : slotTowerArray) {
                slotTower.draw(batch);
            }
            font.draw(
                batch,
                User.getInstance().fullInfo(),
                800 + 24 + 50 + 30,
                480 - 50
            );
        }

        //if (!enemy.isAlive()) {
        //    backgroundMusic.stop();
        //    meowGame.changeScreen(MeowGame.GAMELVL2);
        //}


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
        for (Enemy enemy : waveService.getCurWave().getEnemies()) {
            if (enemy.isAlive()) {
                enemy.nextXY();
                if (enemy.getX() > MeowGame.SCREEN_WIDTH && !camera.isLeftState()
                    || enemy.getX() < MeowGame.SCREEN_WIDTH && camera.isLeftState()) {
                    enemy.draw(batch);
                }
                enemy.setTimeInState(deltaTime);
            }
            if (!waveService.getCurWave().isAliveWave() && waveService.getCurNumberWave() == 0) {
                waveService.nextWave();
                timer.resetTime();
            } else if (!waveService.getCurWave().isAliveWave() && waveService.getCurNumberWave() == 1) {
                waveService.nextWave();
                timer.resetTime();
            } else if (!waveService.getCurWave().isAliveWave() && waveService.getCurNumberWave() == 2) {
                waveService.nextWave();
                timer.resetTime();
            } else if (!waveService.getCurWave().isAliveWave() && waveService.getCurNumberWave() == 3) {
                for (Worker worker : workers) {
                    worker.stopSound();
                }
                User.getInstance().setHp(100);
                User.getInstance().setGold(0);
                User.getInstance().setOre(0);
                User.getInstance().setWood(0);
                meowGame.unlockLevel((byte) 2);
                meowGame.changeScreen(MeowGame.CHANGELVL);
            }
        }
        //debugInfo.draw(batch);
        timer.draw(batch, font, 1400, 20);
        timer.tick(deltaTime);

        batch.end();

        workerService.generateWorker(curTime);

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

    @Override
    public void dispose() {
        background.dispose();
        gameUserInterface.dispose();
        tmpTexture.dispose();
        font.dispose();
        atlasAttack.dispose();
        atlasGoTo.dispose();
        atlasStay.dispose();
        atlasClicked.dispose();
        atlasFall.dispose();
        atlasSleep.dispose();
        atlasWalk.dispose();
        atlasFly.dispose();
        atlasGoFrom.dispose();
        atlasWork.dispose();
        atlasAttack2.dispose();
        atlasGoTo2.dispose();
        atlasStay2.dispose();

        for (Resource resource : resourceList) {
            resource.dispose();
        }
        coreTower.dispose();

        for (Worker worker : workers) {
            worker.stopSound();
            worker.dispose();
        }

        for (DefensiveTower defensiveTower : defensiveTowerArray) {
            defensiveTower.dispose();
        }

        for (SlotTower slotTower : slotTowerArray) {
            slotTower.dispose();
        }

        shop.dispose();
        //shop2.dispose();
        waveService.dispose();

        //debugInfo.dispose();
    }
}

