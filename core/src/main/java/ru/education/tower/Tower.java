package ru.education.tower;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Gdx;

import java.util.EnumMap;

import ru.education.MeowGame;

public abstract class Tower {
    protected final float x;
    protected final float y;
    protected final EnumMap<TowerState, TowerStateAttribute> attributeEnumMap;
    protected TowerState curState;
    protected final Texture debugTexture;

    public Tower(
        float x, float y, EnumMap<TowerState, TowerStateAttribute> attributeEnumMap
    ) {
        this.x = x;
        this.y = y;
        this.attributeEnumMap = attributeEnumMap;
        this.curState = TowerState.DEFAULT;
        debugTexture = new Texture(Gdx.files.internal("tmp.png"));
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public TowerState getCurState() {
        return curState;
    }

    public EnumMap<TowerState, TowerStateAttribute> getAttributeEnumMap() {
        return attributeEnumMap;
    }

    public void setCurState(TowerState curState) {
        this.curState = curState;
    }

    public void dispose() {
        debugTexture.dispose();
    }

}
