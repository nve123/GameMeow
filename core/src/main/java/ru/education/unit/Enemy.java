package ru.education.unit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import ru.education.ui.SettingsUserInterface;
import ru.education.user.User;
import ru.education.util.AnimationUtil;

import java.util.EnumMap;
import java.util.Stack;

public class Enemy extends Unit {

    public static final int TIME_TO_START = 60;
    private Stack<Rectangle> path;
    private float x;
    private float firstX;
    private float y;
    private float firstY;
    private int hp;
    private int firstHp;
    private final BitmapFont font = new BitmapFont();
    private Rectangle curDestination;

    public enum StateEnemy {
        STAY, GO_TO, ATTACK
    }

    private StateEnemy currentState;
    private float timeLastAttack;
    private EnumMap<StateEnemy, StateAttribute> stateAttrMap;
    private boolean rightPosition;
    private TextureAtlas atlasStay;
    private TextureAtlas atlasGoTo;
    private TextureAtlas atlasAttack;
    //public Sound explosionSound;

    public Enemy(int hp, Rectangle destination, float x, float y, Array<Rectangle> pathPoints) {

        this.x = x;
        this.y = y;
        this.hp = hp;
        firstHp = hp;
        firstX = x;
        firstY = y;

        rightPosition = false;
        isAlive = true;

        atlasStay = new TextureAtlas("animations/skeleton_stay.atlas");
        atlasGoTo = new TextureAtlas("animations/skeleton_walk.atlas");
        atlasAttack = new TextureAtlas("animations/skeleton_attack.atlas");
        //explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/destroy.mp3"));
        initStateMap();

        setCurrentState(StateEnemy.STAY);
        setDestination(destination);
        path = new Stack<>();
        path.push(destination);
        for (int i = pathPoints.size - 1; i > 0; i--) {
            path.push(pathPoints.get(i));
        }
        curDestination = pathPoints.get(0);
        calcDeltaXAndDeltaY(curDestination);
        timeLastAttack = -1;
    }
    public Enemy(int hp, Rectangle destination, float x, float y, Array<Rectangle> pathPoints, TextureAtlas atlasStay, TextureAtlas atlasGoTo, TextureAtlas atlasAttack) {

        this.x = x;
        this.y = y;
        this.hp = hp;
        firstHp = hp;
        firstX = x;
        firstY = y;
        rightPosition = false;
        isAlive = true;

        this.atlasStay = atlasStay;
        this.atlasGoTo = atlasGoTo;
        this.atlasAttack = atlasAttack;

        initStateMap();

        setCurrentState(StateEnemy.STAY);
        setDestination(destination);
        path = new Stack<>();
        path.push(destination);
        for (int i = pathPoints.size - 1; i > 0; i--) {
            path.push(pathPoints.get(i));
        }
        curDestination = pathPoints.get(0);
        calcDeltaXAndDeltaY(curDestination);
        timeLastAttack = -1;
    }

    @Override
    public void initStateMap() {
        textureAtlasArray = new Array<>();
        stateAttrMap = new EnumMap<>(StateEnemy.class);

        TextureAtlas atlas = atlasStay;
        stateAttrMap.put(
            StateEnemy.STAY,
            new StateAttribute(
                60f,
                60f,
                AnimationUtil.getAnimationFromAtlas(
                    atlas,
                    2f
                ),
                0f,
                null
            )
        );
        textureAtlasArray.add(atlas);

        atlas = atlasGoTo;
        stateAttrMap.put(
            StateEnemy.GO_TO,
            new StateAttribute(
                60f,
                60f,
                AnimationUtil.getAnimationFromAtlas(
                    atlas,
                    1f
                ),
                0.2f,
                null
            )
        );
        textureAtlasArray.add(atlas);

        atlas = atlasAttack;
        stateAttrMap.put(
            StateEnemy.ATTACK,
            new StateAttribute(
                60f,
                60f,
                AnimationUtil.getAnimationFromAtlas(
                    atlas,
                    0.75f
                ),
                0f,
                null
            )
        );
        textureAtlasArray.add(atlas);
    }

    public void setTimeInState(float deltaTime) {
        this.timeInState += deltaTime;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setCurrentState(StateEnemy currentState) {
        this.currentState = currentState;
        timeInState = 0;
    }

    public TextureRegion getCurrentFrame() {
        return stateAttrMap.get(currentState).animation.getKeyFrame(timeInState, true);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getDeltaX() {
        return deltaX;
    }

    public float getDeltaY() {
        return deltaY;
    }

    public void getDmg(int dmg) {
        hp -= dmg;
        if (hp <= 0) {
            isAlive = false;
            if (SettingsUserInterface.isSoundOn) {
                //explosionSound.play(0.2f);
            }
        }
    }

    public int getHp() {
        return hp;
    }

    @Override
    public float getSpeed() {
        return stateAttrMap.get(currentState).speed;
    }

    @Override
    public float getWidth() {
        return stateAttrMap.get(currentState).width;
    }

    @Override
    public float getHeight() {
        return stateAttrMap.get(currentState).height;
    }

    public float getFirstX() {
        return firstX;
    }

    public float getFirstY() {
        return firstY;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void reviveEnemy(int addHp){
        isAlive = true;
        setX(getFirstX());
        setY(getFirstY());
        hp = firstHp + addHp;
        timeInState = 0;
        setCurrentState(StateEnemy.STAY);
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(
            getCurrentFrame(),
            rightPosition ? x + getWidth() : x,
            y,
            rightPosition ? -getWidth() : getWidth(),
            getHeight()
        );
        font.draw(batch, String.valueOf(hp), x, y + getHeight());
    }

    @Override
    public void nextXY() {
        if (currentState != StateEnemy.STAY
            && currentState != StateEnemy.ATTACK
            && !destination.contains(x, y)) {

            float targetX = curDestination.x + curDestination.width / 2f;
            float targetY = curDestination.y + curDestination.height / 2f;

            float s = (float) Math.sqrt((x - targetX) * (x - targetX) + (y - targetY) * (y - targetY));

            if (s > 0) {
                float deltaS = getSpeed();
                deltaX = (deltaS * Math.abs(x - targetX)) / s;
                deltaY = (deltaS * Math.abs(y - targetY)) / s;
            } else {
                deltaX = 0;
                deltaY = 0;
            }

            if (s <= getSpeed()) {
                x = targetX;
                y = targetY;

                if (path != null && !path.isEmpty()) {
                    curDestination = path.pop();
                }
            } else {
                if (targetY > y) y += deltaY;
                if (targetY < y) y -= deltaY;

                if (targetX >= x) {
                    x += deltaX;
                    rightPosition = true;
                }
                if (targetX < x) {
                    x -= deltaX;
                    rightPosition = false;
                }
            }

        } else {
            switch (currentState) {
                case STAY:
                    if (timeInState > TIME_TO_START) {
                        setCurrentState(StateEnemy.GO_TO);
                    }
                    break;
                case GO_TO:
                    setCurrentState(StateEnemy.ATTACK);
                    break;
                case ATTACK:
                    if ((timeInState - timeLastAttack > 1 || timeLastAttack == -1) && isAlive) {
                        User.getInstance().getDmg(10);
                        timeLastAttack = timeInState;
                    }
                    break;
            }
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        font.dispose();
        //explosionSound.dispose();
    }
}
