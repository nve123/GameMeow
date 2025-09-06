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
import ru.education.shop.Item;
import ru.education.shop.Price;
import ru.education.shop.Shop;
import ru.education.tower.CoreTower;
import ru.education.tower.DefensiveTower;
import ru.education.tower.SlotTower;
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
    private Array<SlotTower> slotTowerArray;
    private Array<DefensiveTower> defensiveTowerArray;
    private Shop shop;

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

        shop = new Shop(MeowGame.SCREEN_WIDTH + 24, 24);
        shop.addItem(
            new Item(
                new Texture(Gdx.files.internal("towerShop50x50.png")),
                new Price(10, 20, 15)
            )
        );

        slotTowerArray = new Array<>(6);
        slotTowerArray.add(new SlotTower(MeowGame.SCREEN_WIDTH + 150, 480 / 2f + 50));
        slotTowerArray.add(new SlotTower(MeowGame.SCREEN_WIDTH + 150 + 100 + 50, 480 / 2f + 50));
        slotTowerArray.add(new SlotTower(MeowGame.SCREEN_WIDTH + 150, 480 / 2f - 50 - 100));
        slotTowerArray.add(new SlotTower(MeowGame.SCREEN_WIDTH + 150 + 100 + 50, 480 / 2f - 50 - 100));
        slotTowerArray.add(new SlotTower(MeowGame.SCREEN_WIDTH + 150 + 100 + 50 + 100 + 50, 480 / 2f + 50));
        slotTowerArray.add(new SlotTower(MeowGame.SCREEN_WIDTH + 150 + 100 + 50 + 100 + 50, 480 / 2f - 50 - 100));

        defensiveTowerArray = new Array<>();
        User.getInstance().incOre(50);
        User.getInstance().incWood(50);
        User.getInstance().incGold(50);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.CLEAR);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        float deltaTime = Gdx.graphics.getDeltaTime();

        if (Gdx.input.justTouched()) {
            camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (!shop.isActive()) {
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

                for (Item item : shop.getItems()) {
                    if (item.getHitBox().contains(touchPoint.x, touchPoint.y)) {
                        for (SlotTower slotTower : slotTowerArray) {
                            if (slotTower.isFree()) slotTower.setVisible(true);
                        }
                        shop.setCurChoice(item);
                        shop.setActive(true);
                    }
                }
            } else {
                for (SlotTower slotTower : slotTowerArray) {
                    if (
                        slotTower.getHitBox().contains(touchPoint.x, touchPoint.y)
                            && slotTower.isFree()
                            && User.getInstance().canBuy(shop.getCurChoice().getPrice())
                    ) {
                        defensiveTowerArray.add(new DefensiveTower(
                            slotTower.getHitBox().x,
                            slotTower.getHitBox().y,
                            70, 125,
                            new Texture(Gdx.files.internal("tower.png"))
                        ));
                        slotTower.setFree(false);
                        slotTower.setVisible(false);
                        User.getInstance().buyItem(shop.getCurChoice());
                    }
                }
                shop.setActive(false);
                for (SlotTower slotTower : slotTowerArray) {
                    if (slotTower.isVisible()) slotTower.setVisible(false);
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
        } else {
            shop.draw(batch);
            for (SlotTower slotTower : slotTowerArray) {
                slotTower.draw(batch);
            }
            for (DefensiveTower defensiveTower : defensiveTowerArray) {
                defensiveTower.draw(batch);
            }
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
        coreTower.dispose();
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
