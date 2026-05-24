package ru.education.screen;

import static ru.education.ui.InfSettingsUserInterface.back;
import static ru.education.ui.InfSettingsUserInterface.numBack;

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
import ru.education.ui.InfSettingsUserInterface;
import ru.education.unit.Enemy;
import ru.education.unit.Worker;
import ru.education.user.User;
import ru.education.wave.Wave;

public class InfinityModeScreen implements Screen {
    private final MeowGame meowGame;
    public static final int WORLD_WIDTH = MeowGame.SCREEN_WIDTH * 2;
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
    private Array<Rectangle> enemyPathPoint;
    //private ShopService shopService2;
    private WaveService waveService;
    public Music backgroundMusic;
    //private Enemy enemy;
    private Array<Rectangle> enemyPathPoint1;
    private Array<Rectangle> enemyPathPoint2;
    private Array<Rectangle> enemyPathPoint3;
    private Array<Enemy> enemiesWave0;
    private Wave wave0;
    private float countX;


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
        InfSettingsUserInterface infSettingsUserInterface = new InfSettingsUserInterface(camera, meowGame);

        background = back;
        //backgroundMusic = null;
        //backgroundMusic.setLooping(true);
        //backgroundMusic.setVolume(0.0f);
        //backgroundMusic.play();


        gameUserInterface = new GameUserInterface(camera, this, meowGame);
        if (numBack == 2) {
            Rectangle hitBoxCoreTower = new Rectangle(
                MeowGame.SCREEN_WIDTH - 40 - 50,
                0 + 30,
                25,
                25
            );

            tmpTexture = new Texture(Gdx.files.internal("tmp.png"));
            coreTower = new Core(
                170,
                226,
                MeowGame.SCREEN_WIDTH  - 226 - 10, 0,
                hitBoxCoreTower
            );
        } else if (numBack == 1) {
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
        } else if (numBack == 3) {
            Rectangle hitBoxCoreTower = new Rectangle(
                190+ 200 + 120,
                480 - 260,
                25,
                25
            );

            tmpTexture = new Texture(Gdx.files.internal("tmp.png"));
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
        workers.add(new Worker(coreTower));
        workers.add(new Worker(coreTower));
        workers.add(new Worker(coreTower));
        workers.add(new Worker(coreTower));
        workers.add(new Worker(coreTower));
        workers.add(new Worker(coreTower));

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
            enemyPathPoint.add(new Rectangle(MeowGame.SCREEN_WIDTH - 170 * 2,MeowGame.SCREEN_HEIGHT / 2f - 226 / 4 + 40, 10, 10));
        } else if (numBack == 3) {
            enemyPathPoint1 = new Array<>();
            enemyPathPoint1.add(new Rectangle(795 - 10 - 30, 480 - 467 - 5, 10, 10));
            enemyPathPoint1.add(new Rectangle(621 - 10 - 30, 480 - 260 - 5, 10, 10));
            enemyPathPoint2 = new Array<>();
            enemyPathPoint2.add(new Rectangle(621 - 10 - 30, 480 - 260 - 5, 10, 10));
            enemyPathPoint3 = new Array<>();
            enemyPathPoint3.add(new Rectangle(795 - 10 - 30, 480 - 26 - 5, 10, 10));
            enemyPathPoint3.add(new Rectangle(621 - 10 - 30, 480 - 260 - 5, 10, 10));
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



        wave0 = new Wave(enemiesWave0);

        waves = new Array<>(1);
        waves.add(wave0);

        waveService = new WaveService(waves);


        font = new BitmapFont();

        shop = new Shop(MeowGame.SCREEN_WIDTH + 24, 24);
        //shop2 = new Shop(24 + 300, 24);
        shop.addItem(ItemType.TOWER);
        //shop2.addItem(ItemType.TOWER);
        shop.addItem(ItemType.UPDATE_DMG);
        //shop2.addItem(ItemType.UPDATE_DMG);
        shop.addItem(ItemType.UPDATE_SPEED);
        shop.addItem(ItemType.UPDATE_DMGPLSPLS);



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
                //shop2.setActive(false);
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
                for (Wave wave:waves) {
                    wave.reviveWave();
                    if (numBack == 1) {
                        enemiesWave0.add(new Enemy(10, coreTower.getHitBox(), WORLD_WIDTH - 100 + countX + 50, MeowGame.SCREEN_HEIGHT / 2f - 226 / 4 + 40, enemyPathPoint));
                    } else if (numBack == 2) {
                        enemiesWave0.add(new Enemy(10, coreTower.getHitBox(), 1554 + countX + 50, 417, enemyPathPoint));
                    } else if (numBack == 3) {
                        enemiesWave0.add(new Enemy(10, coreTower.getHitBox(), 1650 + countX + 50, 480 - 467, enemyPathPoint1));
                    }
                    System.out.println(wave.isAliveWave());
                    countX += 50;
                }
            }
        }

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

    public void setBackground(Texture background1){
        background = background1;
    }

    public Texture getBackground() {
        return background;
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
