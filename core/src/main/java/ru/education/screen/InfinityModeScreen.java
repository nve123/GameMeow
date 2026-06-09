package ru.education.screen;

import static ru.education.ui.InfSettingsUserInterface.back;
import static ru.education.ui.InfSettingsUserInterface.catBack;
import static ru.education.ui.InfSettingsUserInterface.numBack;

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
import ru.education.ui.InfSettingsUserInterface;
import ru.education.ui.SettingsUserInterface;
import ru.education.unit.Enemy;
import ru.education.unit.Worker;
import ru.education.user.User;
import ru.education.util.MathUtil;
import ru.education.wave.Wave;

public class InfinityModeScreen implements Screen {
    private final MeowGame meowGame;
    public static final int WORLD_WIDTH = MeowGame.SCREEN_WIDTH * 2;
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
    private Array<Rectangle> enemyPathPoint;
    //private ShopService shopService2;
    private WaveService waveService;
    //private Enemy enemy;
    private Array<Rectangle> enemyPathPoint1;
    private Array<Rectangle> enemyPathPoint2;
    private Array<Rectangle> enemyPathPoint3;
    private Array<Enemy> enemiesWave0;
    private Wave wave0;
    private float countX;
    private float countX1;
    private float countX2;
    private float countX3;
    private float countY1;
    private float countY2;
    private int addHp;
    TextureAtlas atlasFly;
    TextureAtlas atlasFall;
    TextureAtlas atlasSleep;
    TextureAtlas atlasClicked;
    TextureAtlas atlasWalk;
    TextureAtlas atlasWork;
    TextureAtlas atlasGoFrom;
    private int numCat;


    public InfinityModeScreen(MeowGame meowGame) {
        this.meowGame = meowGame;
    }


    @Override
    public void show() {
        User.getInstance().setHp(100);
        batch = meowGame.getSpriteBatch();
        camera = new OrthographicCameraWithLeftRightState();
        camera.setToOrtho(false, MeowGame.SCREEN_WIDTH, MeowGame.SCREEN_HEIGHT);
        countX = 50;
        countX1 = 50;
        countX2 = 50;
        countX3 = 50;
        countY1 = 50;
        countY2 = 50;
        addHp = 5;
        InfSettingsUserInterface infSettingsUserInterface = new InfSettingsUserInterface(camera, meowGame);

        background = back;
        /*atlasFly = new TextureAtlas("animations/cat_fly.atlas");
        atlasFall = new TextureAtlas("animations/cat_fall.atlas");
        atlasSleep = new TextureAtlas("animations/cat_sleep.atlas");
        atlasClicked = new TextureAtlas("animations/cat_shine.atlas");
        atlasWalk = new TextureAtlas("animations/cat_walk.atlas");
        atlasWork = new TextureAtlas("animations/cat_work.atlas");
        atlasGoFrom = new TextureAtlas("animations/cat_go_to.atlas");*/


        gameUserInterface = new GameUserInterface(camera, this, meowGame);
        if (numBack == 2) {
            Rectangle hitBoxCoreTower = new Rectangle(
                MeowGame.SCREEN_WIDTH - 40 - 50,
                0 + 30,
                25,
                25
            );

            tmpTexture = new Texture(Gdx.files.internal("debug/tmp.png"));
            coreTower = new Core(
                170,
                226,
                MeowGame.SCREEN_WIDTH - 226 - 10, 0,
                hitBoxCoreTower
            );
        } else if (numBack == 1) {
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
        } else if (numBack == 3 || numBack == 4 || numBack == 5) {
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
        }


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
        if (catBack == 1){
            atlasFly = new TextureAtlas("animations/cat_fly.atlas");
            atlasFall = new TextureAtlas("animations/cat_fall.atlas");
            atlasSleep = new TextureAtlas("animations/cat_sleep.atlas");
            atlasClicked = new TextureAtlas("animations/cat_shine.atlas");
            atlasWalk = new TextureAtlas("animations/cat_walk.atlas");
            atlasWork = new TextureAtlas("animations/cat_work.atlas");
            atlasGoFrom = new TextureAtlas("animations/cat_go_to.atlas");
        } else if (catBack == 2) {
            atlasFly = new TextureAtlas("animations/hat_cat_fly.atlas");
            atlasFall = new TextureAtlas("animations/hat_cat_fall.atlas");
            atlasSleep = new TextureAtlas("animations/hat_cat_sleep.atlas");
            atlasClicked = new TextureAtlas("animations/hat_cat_shine.atlas");
            atlasWalk = new TextureAtlas("animations/hat_cat_walk.atlas");
            atlasWork = new TextureAtlas("animations/hat_cat_work.atlas");
            atlasGoFrom = new TextureAtlas("animations/hat_cat_go_to.atlas");
        }
        else if (catBack == 3) {
            atlasFly = new TextureAtlas("animations/cap_cat_fly.atlas");
            atlasFall = new TextureAtlas("animations/cap_cat_fall.atlas");
            atlasSleep = new TextureAtlas("animations/cap_cat_sleep.atlas");
            atlasClicked = new TextureAtlas("animations/cap_cat_shine.atlas");
            atlasWalk = new TextureAtlas("animations/cap_cat_walk.atlas");
            atlasWork = new TextureAtlas("animations/cap_cat_work.atlas");
            atlasGoFrom = new TextureAtlas("animations/cap_cat_go_to.atlas");
        }
        else if (catBack == 4) {
            atlasFly = new TextureAtlas("animations/tigr_cat_fly.atlas");
            atlasFall = new TextureAtlas("animations/tigr_cat_fall.atlas");
            atlasSleep = new TextureAtlas("animations/tigr_cat_sleep.atlas");
            atlasClicked = new TextureAtlas("animations/tigr_cat_shine.atlas");
            atlasWalk = new TextureAtlas("animations/tigr_cat_walk.atlas");
            atlasWork = new TextureAtlas("animations/tigr_cat_work.atlas");
            atlasGoFrom = new TextureAtlas("animations/tigr_cat_go_to.atlas");
        }
        else if (catBack == 5) {
            atlasFly = new TextureAtlas("animations/hero_cat_fly.atlas");
            atlasFall = new TextureAtlas("animations/hero_cat_fall.atlas");
            atlasSleep = new TextureAtlas("animations/hero_cat_sleep.atlas");
            atlasClicked = new TextureAtlas("animations/hero_cat_shine.atlas");
            atlasWalk = new TextureAtlas("animations/hero_cat_walk.atlas");
            atlasWork = new TextureAtlas("animations/hero_cat_work.atlas");
            atlasGoFrom = new TextureAtlas("animations/hero_cat_go_to.atlas");
        }
        else if (catBack == 6) {
            atlasFly = new TextureAtlas("animations/pants_cat_fly.atlas");
            atlasFall = new TextureAtlas("animations/pants_cat_fall.atlas");
            atlasSleep = new TextureAtlas("animations/pants_cat_sleep.atlas");
            atlasClicked = new TextureAtlas("animations/pants_cat_shine.atlas");
            atlasWalk = new TextureAtlas("animations/pants_cat_walk.atlas");
            atlasWork = new TextureAtlas("animations/pants_cat_work.atlas");
            atlasGoFrom = new TextureAtlas("animations/pants_cat_go_to.atlas");
        }



        workers.add(new Worker(coreTower, atlasFly, atlasFall, atlasSleep, atlasClicked, atlasWalk, atlasWork, atlasGoFrom));
        workers.add(new Worker(coreTower, atlasFly, atlasFall, atlasSleep, atlasClicked, atlasWalk, atlasWork, atlasGoFrom));
        workers.add(new Worker(coreTower, atlasFly, atlasFall, atlasSleep, atlasClicked, atlasWalk, atlasWork, atlasGoFrom));
        workers.add(new Worker(coreTower, atlasFly, atlasFall, atlasSleep, atlasClicked, atlasWalk, atlasWork, atlasGoFrom));
        workers.add(new Worker(coreTower, atlasFly, atlasFall, atlasSleep, atlasClicked, atlasWalk, atlasWork, atlasGoFrom));
        workers.add(new Worker(coreTower, atlasFly, atlasFall, atlasSleep, atlasClicked, atlasWalk, atlasWork, atlasGoFrom));

        touchPoint = new Vector3();

        enemyPathPoint = new Array<>();
        if (numBack == 2) {
            enemyPathPoint.add(new Rectangle(840 - 10 - 30, 480 - 29 - 30, 10, 10));
            enemyPathPoint.add(new Rectangle(840 - 10 - 30, 480 - 214 - 23, 10, 10));
            enemyPathPoint.add(new Rectangle(1554 - 30, 480 - 214 - 20, 10, 10));
            enemyPathPoint.add(new Rectangle(1554 - 5 - 30, 480 - 417 - 10, 10, 10));
            enemyPathPoint.add(new Rectangle(695 - 10 - 30, 480 - 417 - 10, 10, 10));
            enemyPathPoint.add(new Rectangle(695 - 10 - 30, 480 - 44 - 10, 10, 10));
            enemyPathPoint.add(new Rectangle(132 - 10 - 30, 480 - 44 - 10, 10, 10));
        } else if (numBack == 1) {
            enemyPathPoint.add(new Rectangle(MeowGame.SCREEN_WIDTH - 170 * 2, MeowGame.SCREEN_HEIGHT / 2f - 226 / 4 + 40, 10, 10));
        } else if (numBack == 3) {
            enemyPathPoint1 = new Array<>();
            enemyPathPoint1.add(new Rectangle(795 - 10 - 30, 480 - 467 - 5, 10, 10));
            enemyPathPoint1.add(new Rectangle(621 - 10 - 30, 480 - 260 - 5, 10, 10));
            enemyPathPoint2 = new Array<>();
            enemyPathPoint2.add(new Rectangle(621 - 10 - 30, 480 - 260 - 5, 10, 10));
            enemyPathPoint3 = new Array<>();
            enemyPathPoint3.add(new Rectangle(795 - 10 - 30, 480 - 26 - 5, 10, 10));
            enemyPathPoint3.add(new Rectangle(621 - 10 - 30, 480 - 260 - 5, 10, 10));
        }else if (numBack == 4) {
            enemyPathPoint1 = new Array<>();
            enemyPathPoint1.add(new Rectangle(1540 - 10 , 480 - 40 - 5 - 10- 15, 25, 25));
            enemyPathPoint1.add(new Rectangle(1280 - 10 - 25, 480 - 40 - 5 - 10- 15, 25, 25));
            enemyPathPoint1.add(new Rectangle(1280 - 10 - 25, 480 - 450 - 5 - 10 - 15, 25, 25));
            enemyPathPoint1.add(new Rectangle(1075 - 10 - 25 , 480 - 450 - 5 - 10 - 15, 25, 25));
            enemyPathPoint1.add(new Rectangle(1075 - 10 - 25 , 480 - 40 - 5 - 10 - 15, 25, 25));
            enemyPathPoint1.add(new Rectangle(870 - 10 - 25 , 480 - 40 - 5 - 10- 15, 25, 25));
            enemyPathPoint1.add(new Rectangle(870 - 10 - 25 , 480 - 260 - 25, 25, 25));
            enemyPathPoint2 = new Array<>();
            enemyPathPoint2.add(new Rectangle(1540 - 10, 480 - 450 - 20 - 10 , 25, 25));
            enemyPathPoint2.add(new Rectangle(1280 - 10 - 25, 480 - 450 - 20 - 10, 25, 25));
            enemyPathPoint2.add(new Rectangle(1280 - 10 - 25, 480 - 40 - 20 - 10 , 25, 25));
            enemyPathPoint2.add(new Rectangle(1075 - 10 - 25, 480 - 40 - 20 - 10 , 25, 25));
            enemyPathPoint2.add(new Rectangle(1075 - 10 - 25, 480 - 450 - 20 - 10 , 25, 25));
            enemyPathPoint2.add(new Rectangle(870 - 10 - 25, 480 - 450 - 20 - 10 , 25, 25));
            enemyPathPoint2.add(new Rectangle(870 - 10 - 25, 480 - 260 - 25, 25, 25));
        }else if (numBack == 5) {
            enemyPathPoint1 = new Array<>();
            enemyPathPoint1.add(new Rectangle(1180 - 10 - 20, 0 + 10, 10, 10));
            enemyPathPoint1.add(new Rectangle(950 , 480 - 260- 10, 10, 10));
            enemyPathPoint2 = new Array<>();
            enemyPathPoint2.add(new Rectangle(1180, 480 - 10, 10, 10));
            enemyPathPoint2.add(new Rectangle(950 , 480 - 260 - 10, 10, 10));
        }
        slotTowerArray = new Array<>(6);
        if (numBack == 2) {
            slotTowerArray.add(new SlotTower(604, 480 - 408));
            slotTowerArray.add(new SlotTower(604, 480 - 170));
            slotTowerArray.add(new SlotTower(153, 480 - 147));
            slotTowerArray.add(new SlotTower(50, 480 - 147));
            slotTowerArray.add(new SlotTower(MeowGame.SCREEN_WIDTH + 84, 480 - 177));
            slotTowerArray.add(new SlotTower(MeowGame.SCREEN_WIDTH + 104, 480 - 391 + 15));
            slotTowerArray.add(new SlotTower(1171, 480 - 177));
            slotTowerArray.add(new SlotTower(1171, 480 - 391 + 15));
            slotTowerArray.add(new SlotTower(1413, 480 - 177));
            slotTowerArray.add(new SlotTower(1413, 480 - 391 + 15));
        } else if (numBack == 1) {
            slotTowerArray.add(new SlotTower(MeowGame.SCREEN_WIDTH + 150, 480 / 2f + 50));
            slotTowerArray.add(new SlotTower(MeowGame.SCREEN_WIDTH + 150 + 100 + 50, 480 / 2f + 50));
            slotTowerArray.add(new SlotTower(MeowGame.SCREEN_WIDTH + 150, 480 / 2f - 50 - 100));
            slotTowerArray.add(new SlotTower(MeowGame.SCREEN_WIDTH + 150 + 100 + 50, 480 / 2f - 50 - 100));
            slotTowerArray.add(new SlotTower(MeowGame.SCREEN_WIDTH + 150 + 100 + 50 + 100 + 50, 480 / 2f + 50));
            slotTowerArray.add(new SlotTower(MeowGame.SCREEN_WIDTH + 150 + 100 + 50 + 100 + 50, 480 / 2f - 50 - 100));
        } else if (numBack == 3) {
            slotTowerArray.add(new SlotTower(410, 480 - 205));
            slotTowerArray.add(new SlotTower(850, 480 - 205));
            slotTowerArray.add(new SlotTower(1150, 480 - 205));
            slotTowerArray.add(new SlotTower(410, 480 - 440 + 15));
            slotTowerArray.add(new SlotTower(850, 480 - 440 + 15));
            slotTowerArray.add(new SlotTower(1150, 480 - 440 + 15));
            slotTowerArray.add(new SlotTower(1450, 480 - 205));
            slotTowerArray.add(new SlotTower(1450, 480 - 440 + 15));
        }
        else if (numBack == 4) {
            slotTowerArray.add(new SlotTower(900, 480 - 424));
            slotTowerArray.add(new SlotTower(900, 480 - 240));
            slotTowerArray.add(new SlotTower(1120, 480 - 424));
            slotTowerArray.add(new SlotTower(1120, 480 - 240));
            slotTowerArray.add(new SlotTower(1340, 480 - 424));
            slotTowerArray.add(new SlotTower(1340, 480 - 240));
        }
        else if (numBack == 5) {
            slotTowerArray.add(new SlotTower(830, 480 - 420));
            slotTowerArray.add(new SlotTower(830, 480 - 205));
            slotTowerArray.add(new SlotTower(1110 + 30, 480 - 290));
            slotTowerArray.add(new SlotTower(1350, 480 - 140));
            slotTowerArray.add(new SlotTower(1350, 480 - 450));
            slotTowerArray.add(new SlotTower(1500, 480 - 320));
        }

        enemiesWave0 = new Array<>();
        if (numBack == 2) {
            enemiesWave0.add(new Enemy(10, coreTower.getHitBox(), 1554, 417, enemyPathPoint));
            enemiesWave0.add(new Enemy(10, coreTower.getHitBox(), 1604, 417, enemyPathPoint));
        } else if (numBack == 1) {
            enemiesWave0.add(new Enemy(10, coreTower.getHitBox(), WORLD_WIDTH - 100, MeowGame.SCREEN_HEIGHT / 2f - 226 / 4 + 40, enemyPathPoint));
            enemiesWave0.add(new Enemy(10, coreTower.getHitBox(), WORLD_WIDTH - 100 + 50, MeowGame.SCREEN_HEIGHT / 2f - 226 / 4 + 40, enemyPathPoint));
        } else if (numBack == 3) {
            enemiesWave0.add(new Enemy(10, coreTower.getHitBox(), 1650, 480 - 467, enemyPathPoint1));
            enemiesWave0.add(new Enemy(10, coreTower.getHitBox(), 1650, 480 - 260, enemyPathPoint2));
        }
        else if (numBack == 4) {
            enemiesWave0.add(new Enemy(10, coreTower.getHitBox(), 1545, 480, enemyPathPoint1));
            enemiesWave0.add(new Enemy(10, coreTower.getHitBox(), 1545, 0 - 50, enemyPathPoint2));

        }else if (numBack == 5) {
            enemiesWave0.add(new Enemy(10, coreTower.getHitBox(), 1600, 480 + 50, enemyPathPoint1));
            enemiesWave0.add(new Enemy(10, coreTower.getHitBox(), 1600, 0 - 50 , enemyPathPoint2));
        }
        timer = new TimerService();
        timer.setActive(true);

        wave0 = new Wave(enemiesWave0);

        waves = new Array<>(1);
        waves.add(wave0);

        waveService = new WaveService(waves);


        font = new BitmapFont();

        shop = new Shop(MeowGame.SCREEN_WIDTH + 24, 24);
        shop.addItem(ItemType.TOWER);
        shop.addItem(ItemType.UPDATE_DMG);
        shop.addItem(ItemType.UPDATE_SPEED);
        shop.addItem(ItemType.UPDATE_DMGPLSPLS);


        defensiveTowerArray = new Array<>();

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

        for (Enemy enemy : waveService.getCurWave().getEnemies()) {
            for (DefensiveTower defensiveTower : defensiveTowerArray) {
                defensiveTower.draw(batch, enemy, curTime);
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
                for (Wave wave : waves) {
                    wave.reviveWave(addHp);
                    timer.resetTime();
                    if (numBack == 1) {
                        enemiesWave0.add(new Enemy(10, coreTower.getHitBox(), WORLD_WIDTH - 100 + countX + 50, MeowGame.SCREEN_HEIGHT / 2f - 226 / 4 + 40, enemyPathPoint));
                    } else if (numBack == 2) {
                        enemiesWave0.add(new Enemy(10, coreTower.getHitBox(), 1554 + countX + 50, 417, enemyPathPoint));
                    } else if (numBack == 3) {
                        int randomNumber = MathUtil.getRandomNumber(1, 3);
                        if (randomNumber == 1) {
                            enemiesWave0.add(new Enemy(10, coreTower.getHitBox(), 1650 + countX1 + 50, 480 - 467, enemyPathPoint1));
                            countX1 += 50;
                        } else if (randomNumber == 2) {
                            enemiesWave0.add(new Enemy(10, coreTower.getHitBox(), 1650 + countX2 + 50, 480 - 260, enemyPathPoint2));
                            countX2 += 50;
                        } else {
                            enemiesWave0.add(new Enemy(10, coreTower.getHitBox(), 1650 + countX3 + 50, 480 - 26, enemyPathPoint3));
                            countX3 += 50;
                        }
                    } else if (numBack == 4) {
                        int randomNumber = MathUtil.getRandomNumber(1, 2);
                        if (randomNumber == 1) {
                            enemiesWave0.add(new Enemy(10, coreTower.getHitBox(), 1545, 480 + countY1, enemyPathPoint1));
                            countY1 += 50;
                        } else if (randomNumber == 2) {
                            enemiesWave0.add(new Enemy(10, coreTower.getHitBox(), 1545, 0 - 50 - countY1, enemyPathPoint2));
                            countY2 += 50;
                        }
                    }
                    else if (numBack == 5) {
                        int randomNumber = MathUtil.getRandomNumber(1, 2);
                        if (randomNumber == 1) {
                            enemiesWave0.add(new Enemy(10, coreTower.getHitBox(), 1600 + countX1, 480 + 50 + countY1, enemyPathPoint1));
                            countY1 += 50;
                            countX1 += 50;
                        } else if (randomNumber == 2) {
                            enemiesWave0.add(new Enemy(10, coreTower.getHitBox(), 1600 + countX2, 0 - 50 - countY2 , enemyPathPoint2));
                            countY2 += 50;
                            countX2 += 50;
                        }

                    }
                    addHp += 5;
                    countX += 50;
                }
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


        //debugInfo.draw(batch);

        timer.draw(batch, font, 1400, 0);
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

    public void setBackground(Texture background1) {
        background = background1;
    }

    public Texture getBackground() {
        return background;
    }

    @Override
    public void pause() {
        for (Worker worker : workers) {
            worker.stopSound();
        }}

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

        for (Worker worker : workers) {
            worker.stopSound();
            worker.dispose();
        }
        background.dispose();
        gameUserInterface.dispose();
        tmpTexture.dispose();
        font.dispose();
        atlasClicked.dispose();
        atlasFall.dispose();
        atlasSleep.dispose();
        atlasWalk.dispose();
        atlasFly.dispose();
        atlasGoFrom.dispose();
        atlasWork.dispose();

        for (Resource resource : resourceList) {
            resource.dispose();
        }
        coreTower.dispose();


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
