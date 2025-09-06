package ru.education.unit;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import ru.education.MeowGame;
import ru.education.tower.CoreTower;
import ru.education.tower.resource.Resource;
import ru.education.user.User;
import ru.education.util.AnimationUtil;
import ru.education.util.MathUtil;

import java.util.EnumMap;

public class Worker extends Unit {
    private static final float WORKING_TIME = 5f;
    private Resource workingPlace;
    private final CoreTower coreTower;

    public enum StateWorker {
        FLY,
        SLEEP,
        GO_TO,
        GO_FROM,
        FALL,
        CLICKED,
        WORK
    }

    private EnumMap<StateWorker, StateAttribute> stateAttrMap;
    private StateWorker currentState;

    //нужно ли развернуть спрайт по оси Х (если движение в обратном направлении)
    private boolean rightPosition;

    public Worker(CoreTower coreTower) {

        initStateMap();
        this.coreTower = coreTower;
        isAlive = true;
        rightPosition = true;

        //важно состояние задавать до места назначения, т.к. в месте назначения
        //высчитывается изменение координат для состояния, а скорости разные для
        //разных состояний
        setCurrentState(StateWorker.FLY);

        //Координаты спавна рабочих от 200 до 600 по Х
        x = MathUtil.getRandomNumber(
            300,
            (int) (coreTower.getX() - getWidth())
        );
        y = 480;

        setDestination(x, 0);
    }

    public void initStateMap() {
        textureAtlasArray = new Array<>();
        stateAttrMap = new EnumMap<>(StateWorker.class);

        TextureAtlas atlas = new TextureAtlas("catflysupersmall.atlas");
        stateAttrMap.put(
            StateWorker.FLY,
            new StateAttribute(
                42f,
                75f,
                AnimationUtil.getAnimationFromAtlas(
                    atlas,
                    2.5f
                ),
                0.5f
            )
        );
        textureAtlasArray.add(atlas);

        atlas = new TextureAtlas("catfall.atlas");
        stateAttrMap.put(
            StateWorker.FALL,
            new StateAttribute(
                37.8f,
                67.5f,
                AnimationUtil.getAnimationFromAtlas(
                    atlas,
                    5.5f
                ),
                2f
            )
        );
        textureAtlasArray.add(atlas);

        atlas = new TextureAtlas("catsleep.atlas");
        stateAttrMap.put(
            StateWorker.SLEEP,
            new StateAttribute(
                45f,
                39.5f,
                AnimationUtil.getAnimationFromAtlas(
                    atlas,
                    2f
                ),
                0f
            )
        );
        textureAtlasArray.add(atlas);

        atlas = new TextureAtlas("catshine.atlas");
        stateAttrMap.put(
            StateWorker.CLICKED,
            new StateAttribute(
                45f,
                39.5f,
                AnimationUtil.getAnimationFromAtlas(
                    atlas,
                    2f
                ),
                0f
            )
        );
        textureAtlasArray.add(atlas);

        atlas = new TextureAtlas("catwalk.atlas");
        stateAttrMap.put(
            StateWorker.GO_TO,
            new StateAttribute(
                35f,
                35f,
                AnimationUtil.getAnimationFromAtlas(
                    atlas,
                    1f
                ),
                0.75f
            )
        );
        textureAtlasArray.add(atlas);

        atlas = new TextureAtlas("catwork.atlas");
        stateAttrMap.put(
            StateWorker.WORK,
            new StateAttribute(
                35f,
                35f,
                AnimationUtil.getAnimationFromAtlas(
                    atlas,
                    0.75f
                ),
                0
            )
        );
        textureAtlasArray.add(atlas);

        atlas = new TextureAtlas("catcarrysupersmall.atlas");
        stateAttrMap.put(
            StateWorker.GO_FROM,
            new StateAttribute(
                35f,
                35f,
                AnimationUtil.getAnimationFromAtlas(
                    atlas,
                    1f
                ),
                0.75f
            )
        );
        textureAtlasArray.add(atlas);
    }

    public TextureRegion getCurrentFrame() {
        return stateAttrMap.get(currentState).animation.getKeyFrame(timeInState, true);
    }

    public StateWorker getCurrentState() {
        return currentState;
    }

    public void setCurrentState(StateWorker currentState) {
        this.currentState = currentState;
        timeInState = 0;
    }

    public void setTimeInState(float deltaTimeInState) {
        this.timeInState += deltaTimeInState;
    }

    public float getWidth() {
        return stateAttrMap.get(currentState).width;
    }

    public float getHeight() {
        return stateAttrMap.get(currentState).height;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setWorkingPlace(Resource workingPlace) {
        this.workingPlace = workingPlace;
    }

    public float getSpeed() {
        return stateAttrMap.get(currentState).speed;
    }

    public boolean contains(float x, float y) {
        return x >= this.x && x <= this.x + getWidth()
            && y >= this.y && y <= this.y + getHeight();
    }

    public void nextXY() {

        if (currentState != StateWorker.SLEEP && currentState != StateWorker.CLICKED)
            if (!destination.contains(x, y)) {

                if (destination.y + destination.height / 2f > y) y += deltaY;
                if (destination.y + destination.height / 2f < y) y -= deltaY;

                if (destination.x + destination.width / 2f >= x) {
                    x += deltaX;
                    rightPosition = true;
                }
                if (destination.x + destination.width / 2f < x) {
                    x -= deltaX;
                    rightPosition = false;
                }

            } else {

                switch (currentState) {
                    case FLY:
                        isAlive = false;
                        break;
                    case FALL:
                        setCurrentState(StateWorker.SLEEP);
                        break;
                    case GO_TO:
                        setCurrentState(StateWorker.WORK);
                        break;
                    case WORK:
                        if (timeInState >= WORKING_TIME) {
                            setCurrentState(StateWorker.GO_FROM);
                            setDestination(coreTower.getStorageBox());
                        }
                        break;
                    case GO_FROM:

                        setCurrentState(StateWorker.GO_TO);

                        switch (workingPlace.getType()) {
                            case GOLD:
                                User.getInstance().incGold(10);
                                break;
                            case ORE:
                                User.getInstance().incOre(20);
                                break;
                            case WOOD:
                                User.getInstance().incWood(15);
                                break;
                        }

                        setDestination(workingPlace.getWorkBox());
                        break;
                }

            }

    }

    public void clicked() {
        switch (currentState) {
            case FLY:
                if (isAlive) {
                    setCurrentState(StateWorker.FALL);

                    float randY;
                    if (y < (float) MeowGame.SCREEN_HEIGHT / 2)
                        randY = MathUtil.getRandomNumber((int) getHeight(), (int) y);
                    else
                        randY = MathUtil.getRandomNumber((int) getHeight(), MeowGame.SCREEN_HEIGHT / 2);

                    setDestination(x, randY);
                }
                break;
            case SLEEP:
                setCurrentState(StateWorker.CLICKED);
                break;
        }
    }

    public void draw(SpriteBatch batch) {
        batch.draw(
            getCurrentFrame(),
            rightPosition ? x + getWidth() : x,
            y,
            rightPosition ? -getWidth() : getWidth(),
            getHeight()
        );
    }

    public void sleepSametime() {
        if ((getCurrentState() == StateWorker.GO_TO)
            && Math.random() < 0.0005
        ) {
            setCurrentState(StateWorker.SLEEP);
        }
    }
}
