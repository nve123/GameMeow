package ru.education.unit;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import ru.education.MeowGame;
import ru.education.service.MemoryService;
import ru.education.tower.Core;
import ru.education.tower.resource.Resource;
import ru.education.ui.SettingsUserInterface;
import ru.education.user.User;
import ru.education.util.AnimationUtil;
import ru.education.util.MathUtil;

import java.util.EnumMap;

public class Worker extends Unit {
    private static final float WORKING_TIME = 5f;
    private Resource workingPlace;
    private final Core coreTower;
    private Sound sleapSound = Gdx.audio.newSound(Gdx.files.internal("sounds/hrap.mp3"));
    private Sound popBollunSound = Gdx.audio.newSound(Gdx.files.internal("sounds/a-short-clear-sound-of-a-bursting-balloon.mp3"));
    private Sound meowSound = Gdx.audio.newSound(Gdx.files.internal("sounds/cat-meow_fyvriu4d.mp3"));
    private TextureAtlas atlasFly;
    private TextureAtlas atlasFall;
    private TextureAtlas atlasSleep;
    private TextureAtlas atlasClicked;
    private TextureAtlas atlasWalk;
    private TextureAtlas atlasWork;
    private TextureAtlas atlasGoFrom;

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
    private boolean rightPosition;

    public Worker(Core coreTower) {

        this.coreTower = coreTower;
        isAlive = true;
        rightPosition = true;

        atlasFly = new TextureAtlas("animations/cat_fly.atlas");
        atlasFall = new TextureAtlas("animations/cat_fall.atlas");
        atlasSleep = new TextureAtlas("animations/cat_sleep.atlas");
        atlasClicked = new TextureAtlas("animations/cat_shine.atlas");
        atlasWalk = new TextureAtlas("animations/cat_walk.atlas");
        atlasWork = new TextureAtlas("animations/cat_work.atlas");
        atlasGoFrom = new TextureAtlas("animations/cat_go_to.atlas");

        initStateMap();
        setCurrentState(StateWorker.FLY);

        x = MathUtil.getRandomNumber(
            300,
            (int) (coreTower.getX() - getWidth())
        );
        y = 480;

        setDestination(x, 0);
    }

    public Worker(Core coreTower, TextureAtlas atlasFly, TextureAtlas atlasFall, TextureAtlas atlasSleep, TextureAtlas atlasClicked, TextureAtlas atlasWalk, TextureAtlas atlasWork, TextureAtlas atlasGoFrom) {

        this.coreTower = coreTower;
        isAlive = true;
        rightPosition = true;

        this.atlasFly = atlasFly;
        this.atlasFall = atlasFall;
        this.atlasSleep = atlasSleep;
        this.atlasClicked = atlasClicked;
        this.atlasWalk = atlasWalk;
        this.atlasWork = atlasWork;
        this.atlasGoFrom = atlasGoFrom;

        initStateMap();
        setCurrentState(StateWorker.FLY);

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

        stateAttrMap.put(
            StateWorker.FLY,
            new StateAttribute(
                50f,
                95f,
                AnimationUtil.getAnimationFromAtlas(
                    atlasFly,
                    2.5f
                ),
                0.5f,
                null
            )
        );
        textureAtlasArray.add(atlasFly);

        stateAttrMap.put(
            StateWorker.FALL,
            new StateAttribute(
                50f,
                95f,
                AnimationUtil.getAnimationFromAtlas(
                    atlasFall,
                    3.0f
                ),
                2f,
                popBollunSound
            )
        );
        textureAtlasArray.add(atlasFall);

        stateAttrMap.put(
            StateWorker.SLEEP,
            new StateAttribute(
                75f,
                50f,
                AnimationUtil.getAnimationFromAtlas(
                    atlasSleep,
                    2f
                ),
                0f,
                sleapSound
            )
        );
        textureAtlasArray.add(atlasSleep);

        stateAttrMap.put(
            StateWorker.CLICKED,
            new StateAttribute(
                75f,
                50f,
                AnimationUtil.getAnimationFromAtlas(
                    atlasClicked,
                    2f
                ),
                0f,
                meowSound
            )
        );
        textureAtlasArray.add(atlasClicked);

        stateAttrMap.put(
            StateWorker.GO_TO,
            new StateAttribute(
                60f,
                60f,
                AnimationUtil.getAnimationFromAtlas(
                    atlasWalk,
                    1f
                ),
                0.75f,
                null
            )
        );
        textureAtlasArray.add(atlasWalk);

        stateAttrMap.put(
            StateWorker.WORK,
            new StateAttribute(
                60f,
                60f,
                AnimationUtil.getAnimationFromAtlas(
                    atlasWork,
                    0.75f
                ),
                0,
                null
            )
        );
        textureAtlasArray.add(atlasWork);

        stateAttrMap.put(
            StateWorker.GO_FROM,
            new StateAttribute(
                60f,
                60f,
                AnimationUtil.getAnimationFromAtlas(
                    atlasGoFrom,
                    1f
                ),
                0.75f,
                null
            )
        );
        textureAtlasArray.add(atlasGoFrom);
    }

    public TextureRegion getCurrentFrame() {
        return stateAttrMap.get(currentState).animation.getKeyFrame(timeInState, true);
    }

    public StateWorker getCurrentState() {
        return currentState;
    }

    public void setCurrentState(StateWorker currentState) {
        if (this.currentState != null) {
            Sound currentSound = getStateSound();
            if (currentSound != null) {
                currentSound.stop();
            }
        }
        this.currentState = currentState;
        timeInState = 0;
        playSound();
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

    public Sound getStateSound() {
        return stateAttrMap.get(currentState).sound;
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
                                //MemoryService.saveUser();
                                break;
                            case ORE:
                                User.getInstance().incOre(20);
                                //MemoryService.saveUser();
                                break;
                            case WOOD:
                                User.getInstance().incWood(15);
                                //MemoryService.saveUser();
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
            !rightPosition ? x + getWidth() : x,
            y,
            !rightPosition ? -getWidth() : getWidth(),
            getHeight()
        );
    }

    public void playSound() {
        if (SettingsUserInterface.isSoundOn) {
            Sound sound = getStateSound();
            if (sound != null) {
                if (currentState == StateWorker.SLEEP) {
                    sound.loop(0.2f);
                } else {
                    sound.play(0.2f);
                }
            }
        }
    }

    public void stopSound() {
        Sound sound = getStateSound();
        if (sound != null) {
            sound.pause();
        }
    }

    public void sleepSametime() {
        if ((getCurrentState() == StateWorker.GO_TO)
            && Math.random() < 0.0005
        ) {
            setCurrentState(StateWorker.SLEEP);
        }
    }

    @Override
    public void dispose() {
        stopSound();
        if (sleapSound != null) sleapSound.dispose();
        if (popBollunSound != null) popBollunSound.dispose();
        if (meowSound != null) meowSound.dispose();
        super.dispose();
    }
}
