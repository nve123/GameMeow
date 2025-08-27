package ru.education.unit;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.education.util.AnimationUtil;

import java.util.EnumMap;

public class Enemy extends Unit{
    public static final int TIME_TO_START = 10;
    private int hp;
    private final BitmapFont font = new BitmapFont();

    public enum StateEnemy {
        STAY, GO_TO, ATTACK
    }

    private StateEnemy currentState;
    private float timeLastAttack;
    private EnumMap<StateEnemy, StateAttribute> stateAttrMap;

    public Enemy(int hp, Rectangle destination, float x, float y) {
        initStateMap();

        this.x = x;
        this.y = y;
        this.hp = hp;

        isAlive = true;

        setCurrentState(StateEnemy.STAY);
        setDestination(destination);

        timeLastAttack = -1;
    }

    @Override
    public void initStateMap() {
        stateAttrMap = new EnumMap<>(StateEnemy.class);

        stateAttrMap.put(
            StateEnemy.STAY,
            new StateAttribute(
                60f,
                60f,
                AnimationUtil.getAnimationFromAtlas(
                    "enemystay.atlas",
                    2f
                ),
                0f
            )
        );

        stateAttrMap.put(
            StateEnemy.GO_TO,
            new StateAttribute(
                60f,
                60f,
                AnimationUtil.getAnimationFromAtlas(
                    "enemy.atlas",
                    1f
                ),
                0.2f
            )
        );

        stateAttrMap.put(
            StateEnemy.ATTACK,
            new StateAttribute(
                60f,
                60f,
                AnimationUtil.getAnimationFromAtlas(
                    "enemyatack.atlas",
                    0.75f
                ),
                0f
            )
        );
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
        if (hp <= 0) isAlive = false;
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

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(getCurrentFrame(), x, y, getWidth(), getHeight());
        font.draw(batch, String.valueOf(hp), x, y + getHeight());
    }

    @Override
    public void nextXY() {
        if (currentState != StateEnemy.STAY
            && currentState != StateEnemy.ATTACK
            && !destination.contains(x, y)) {
            if (destination.y + destination.height / 2f > y) y += deltaY;
            if (destination.y + destination.height / 2f < y) y -= deltaY;

            if (destination.x + destination.width / 2f >= x) x += deltaX;
            if (destination.x + destination.width / 2f < x) x -= deltaX;

        } else {
            switch (currentState) {
                case STAY:
                    if (timeInState > TIME_TO_START) {
                        setCurrentState(StateEnemy.GO_TO);
                        setDestination(destination);
                    }
                    break;
                case GO_TO:
                    setCurrentState(StateEnemy.ATTACK);
                    break;
                case ATTACK:
                    if ((timeInState - timeLastAttack > 1 || timeLastAttack == -1) && isAlive) {
                        //TODO: отнимать хп у здания
                        timeLastAttack = timeInState;
                    }
            }
        }
    }
}
