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
import ru.education.service.ShopService;
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
import ru.education.unit.Enemy;
import ru.education.unit.Worker;
import ru.education.user.User;
import ru.education.wave.Wave;

public class GameScreenLvl3 implements Screen {
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
    private Array<Wave> waves;
    private BitmapFont font;
    private Array<SlotTower> slotTowerArray;
    private Array<DefensiveTower> defensiveTowerArray;
    private Shop shop;
    //private Shop shop2;
    private float curTime;
    private DebugInfo debugInfo;
    private ShopService shopService;
    private WorkerService workerService;
    private Array<Rectangle> enemyPathPoint1;
    private Array<Rectangle> enemyPathPoint2;
    private Array<Rectangle> enemyPathPoint3;
    //private ShopService shopService2;
    private WaveService waveService;
    public Music backgroundMusic;
    //private Enemy enemy;
    TextureAtlas atlasAttack;
    TextureAtlas atlasGoTo;
    TextureAtlas atlasStay;

    public GameScreenLvl3(MeowGame meowGame) {
        this.meowGame = meowGame;
    }

    @Override
    public void show() {
        User.getInstance().setHp(100);
        batch = meowGame.getSpriteBatch();

        camera = new OrthographicCameraWithLeftRightState();
        camera.setToOrtho(false, MeowGame.SCREEN_WIDTH, MeowGame.SCREEN_HEIGHT);

        background = new Texture(Gdx.files.internal("backgrounds/game_back_lvl3.png"));
        backgroundMusic = null;
        //backgroundMusic.setLooping(true);
        //backgroundMusic.setVolume(0.0f);
        //backgroundMusic.play();

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
        Rectangle workBoxGold = new Rectangle(130 - 25 + 170 - 20 - 50, 0, 15f, 15f);
        resourceGold = new Resource(130, 0, ResourceType.GOLD, workBoxGold);
        Rectangle workBoxOre = new Rectangle(130 - 25 + 146 - 20 - 10 - 10, 480 - 120, 15f, 15f);
        resourceOre = new Resource(130, 480 - 120, ResourceType.ORE, workBoxOre);
        Rectangle workBoxWood = new Rectangle(34 - 25 + 150 - 20 - 20, 480 - 300, 15f, 15f);
        resourceWood = new Resource(34, 480 - 300 - 15, ResourceType.WOOD, workBoxWood);
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

        enemyPathPoint1 = new Array<>();
        enemyPathPoint1.add(new Rectangle(795 - 10 - 30, 480 - 467 - 5, 10, 10));
        enemyPathPoint1.add(new Rectangle(621 - 10 - 30, 480 - 260 - 5, 10, 10));
        enemyPathPoint2 = new Array<>();
        enemyPathPoint2.add(new Rectangle(621 - 10 - 30, 480 - 260 - 5, 10, 10));
        enemyPathPoint3 = new Array<>();
        enemyPathPoint3.add(new Rectangle(795 - 10 - 30, 480 - 26 - 5, 10, 10));
        enemyPathPoint3.add(new Rectangle(621 - 10 - 30, 480 - 260 - 5, 10, 10));

        Array<Enemy> enemiesWave0 = new Array<>();
        Array<Enemy> enemiesWave1 = new Array<>();
        Array<Enemy> enemiesWave2 = new Array<>();
        Array<Enemy> enemiesWave3 = new Array<>();

        enemiesWave0.add(new Enemy(10, coreTower.getHitBox(), 1650, 480 - 467, enemyPathPoint1));
        enemiesWave0.add(new Enemy(10, coreTower.getHitBox(), 1650, 480 - 260, enemyPathPoint2));

        enemiesWave1.add(new Enemy(10, coreTower.getHitBox(), 1650, 480 - 260, enemyPathPoint2));
        enemiesWave1.add(new Enemy(10, coreTower.getHitBox(), 1650, 480 - 467, enemyPathPoint1));
        enemiesWave1.add(new Enemy(10, coreTower.getHitBox(), 1650, 480 - 26, enemyPathPoint3));

        enemiesWave2.add(new Enemy(10, coreTower.getHitBox(), 1700, 480 - 26, enemyPathPoint3));
        enemiesWave2.add(new Enemy(10, coreTower.getHitBox(), 1650, 480 - 260, enemyPathPoint2));
        enemiesWave2.add(new Enemy(10, coreTower.getHitBox(), 1650, 480 - 467, enemyPathPoint1));
        enemiesWave2.add(new Enemy(10, coreTower.getHitBox(), 1700, 480 - 467, enemyPathPoint1));
        enemiesWave2.add(new Enemy(10, coreTower.getHitBox(), 1650, 480 - 26, enemyPathPoint3));

        enemiesWave3.add(new Enemy(
                300,
                coreTower.getHitBox(),
                1650,
                480 - 260,
                enemyPathPoint2,
                atlasStay = new TextureAtlas("animations/slime_stay.atlas"),
                atlasGoTo = new TextureAtlas("animations/slime_walk.atlas"),
                atlasAttack = new TextureAtlas("animations/slime_attack.atlas")
            )
        );

        Wave wave0 = new Wave(enemiesWave0);
        Wave wave1 = new Wave(enemiesWave1);
        Wave wave2 = new Wave(enemiesWave2);
        Wave wave3 = new Wave(enemiesWave3);

        waves = new Array<>(4);
        waves.add(wave0, wave1, wave2, wave3);

        waveService = new WaveService(waves);


        font = new BitmapFont();

        shop = new Shop(MeowGame.SCREEN_WIDTH + 24, 24);
        //shop2 = new Shop(24 + 300, 24);
        shop.addItem(ItemType.TOWER);
        //shop2.addItem(ItemType.TOWER);
        shop.addItem(ItemType.UPDATE_DMG);
        //shop2.addItem(ItemType.UPDATE_DMG);
        shop.addItem(ItemType.UPDATE_SPEED);
        //shop2.addItem(ItemType.UPDATE_SPEED);

        slotTowerArray = new Array<>(8);
        slotTowerArray.add(new SlotTower(410, 480 - 205));
        slotTowerArray.add(new SlotTower(850, 480 - 205));
        slotTowerArray.add(new SlotTower(1150, 480 - 205));
        slotTowerArray.add(new SlotTower(410, 480 - 440 + 15));
        slotTowerArray.add(new SlotTower(850, 480 - 440 + 15));
        slotTowerArray.add(new SlotTower(1150, 480 - 440 + 15));
        slotTowerArray.add(new SlotTower(1450, 480 - 205));
        slotTowerArray.add(new SlotTower(1450, 480 - 440 + 15));


        defensiveTowerArray = new Array<>();

        curTime = 0f;

        shopService = new ShopService(slotTowerArray, defensiveTowerArray, shop);
        //shopService2 = new ShopService(slotTowerArray, defensiveTowerArray, shop2);
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
            /*if (!shop2.isActive()) {
                workerService.workerClickProcessing(touchPoint);
                shopService2.shopItemClickProcessing(touchPoint);
            } else {
                shopService2.shoppingProcess(touchPoint);
                shop2.setActive(false);
            }*/
        }

        batch.begin();

        batch.draw(background, 0, 0, MeowGame.SCREEN_WIDTH * 2, MeowGame.SCREEN_HEIGHT);

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
            } else if (!waveService.getCurWave().isAliveWave() && waveService.getCurNumberWave() == 1) {
                waveService.nextWave();
            } else if (!waveService.getCurWave().isAliveWave() && waveService.getCurNumberWave() == 2) {
                waveService.nextWave();
            } else if (!waveService.getCurWave().isAliveWave() && waveService.getCurNumberWave() == 3) {
                for (Worker worker : workers) {
                    worker.stopSound();
                }
                //backgroundMusic.stop();
                User.getInstance().setHp(100);
                User.getInstance().setGold(100);
                User.getInstance().setOre(200);
                User.getInstance().setWood(150);
                meowGame.unlockLevel((byte) 2);
                meowGame.changeScreen(MeowGame.CHANGELVL);
            }
        }

        //if (!enemy.isAlive()) {
        //    backgroundMusic.stop();
        //    meowGame.changeScreen(MeowGame.GAMELVL2);
        //}

        for (Enemy enemy : waveService.getCurWave().getEnemies()) {
            for (DefensiveTower defensiveTower : defensiveTowerArray) {
                defensiveTower.draw(batch, enemy, curTime);
            }
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
        //backgroundMusic.dispose();
        background.dispose();
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

        for (Worker worker : workers) {
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

        debugInfo.dispose();
    }
}
