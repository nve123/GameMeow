package ru.education.tower.resource;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.util.EnumMap;

import ru.education.tower.TowerState;
import ru.education.tower.TowerStateAttribute;

public class Resource {
    private final Rectangle hitBox;
    private Texture texture;
    private ResourceType type;
    private final float x;
    private final float y;
    private float width;
    private float height;
    private ResourceState curState = ResourceState.DEFAULT;
    private ResourceState prevState;
    private Rectangle workBox;
    //private Texture debugResourceTexture;
    EnumMap<ResourceState, ResourceStateAttribute> oreStateAttributeEnumMap = new EnumMap<>(ResourceState.class);
    EnumMap<ResourceState, ResourceStateAttribute> goldStateAttributeEnumMap = new EnumMap<>(ResourceState.class);
    EnumMap<ResourceState, ResourceStateAttribute> woodStateAttributeEnumMap = new EnumMap<>(ResourceState.class);

    public Resource(float x, float y, ResourceType type, Rectangle workBox) {
        switch (type) {
            case ORE -> {
                ResourceStateAttribute resourceStateAttributeDefault = new ResourceStateAttribute(
                    texture = new Texture(Gdx.files.internal("level_construct_assets/ore.png")),
                    width = 100,
                    height = 100
                );
                ResourceStateAttribute resourceStateAttributeShine = new ResourceStateAttribute(
                    texture = new Texture(Gdx.files.internal("level_construct_assets/ore_shine.png")),
                    width = 100,
                    height = 100
                );
                oreStateAttributeEnumMap.put(ResourceState.DEFAULT, resourceStateAttributeDefault);
                oreStateAttributeEnumMap.put(ResourceState.SHINE, resourceStateAttributeShine);
            }
            case GOLD -> {
                ResourceStateAttribute resourceStateAttributeDefault = new ResourceStateAttribute(
                    texture = new Texture(Gdx.files.internal("level_construct_assets/gold.png")),
                    width = 100,
                    height = 100
                );
                ResourceStateAttribute resourceStateAttributeShine = new ResourceStateAttribute(
                    texture = new Texture(Gdx.files.internal("level_construct_assets/gold_shine.png")),
                    width = 100,
                    height = 100
                );
                goldStateAttributeEnumMap.put(ResourceState.DEFAULT, resourceStateAttributeDefault);
                goldStateAttributeEnumMap.put(ResourceState.SHINE, resourceStateAttributeShine);
            }
            case WOOD -> {
                ResourceStateAttribute resourceStateAttributeDefault = new ResourceStateAttribute(
                    texture = new Texture(Gdx.files.internal("level_construct_assets/wood.png")),
                width = 100,
                height = 100
                );
                ResourceStateAttribute resourceStateAttributeShine = new ResourceStateAttribute(
                    texture = new Texture(Gdx.files.internal("level_construct_assets/wood_shine.png")),
                width = 100,
                height = 100
                );
                woodStateAttributeEnumMap.put(ResourceState.DEFAULT, resourceStateAttributeDefault);
                woodStateAttributeEnumMap.put(ResourceState.SHINE, resourceStateAttributeShine);
            }

        }
        this.x = x;
        this.y = y;
        this.type = type;
        //this.debugResourceTexture = new Texture(Gdx.files.internal("debug/tmp.png"));
        hitBox = new Rectangle(x, y, this.width, this.height);
        this.workBox = workBox;
    }
    public void setCurState(ResourceState curState) {
        prevState = this.curState;
        this.curState = curState;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public Texture getTexture() {
        return texture;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public boolean contains(float x, float y) {
        return x >= this.x && x <= this.x + width
            && y >= this.y && y <= this.y + height;
    }

    public ResourceType getType() {
        return type;
    }

    public Rectangle getWorkBox() {
        return workBox;
    }

    public void draw(SpriteBatch batch) {
        if (type == ResourceType.ORE) {
            batch.draw(oreStateAttributeEnumMap.get(curState).getTexture(), x, y, width, height);
        }
        else if (type == ResourceType.GOLD) {
            batch.draw(goldStateAttributeEnumMap.get(curState).getTexture(), x, y, width, height);
        }
        else if (type == ResourceType.WOOD) {
            batch.draw(woodStateAttributeEnumMap.get(curState).getTexture(), x, y, width, height);
        }

        //batch.draw(debugResourceTexture, workBox.x, workBox.y, workBox.width, workBox.height);
        //batch.draw(new Texture(Gdx.files.internal("tmp.png")), hitBox.x, hitBox.y, hitBox.width, hitBox.height);
    }

    public void dispose() {
        if (type == ResourceType.ORE) {
            oreStateAttributeEnumMap.get(curState).getTexture().dispose();
        }
        else if (type == ResourceType.GOLD) {
            goldStateAttributeEnumMap.get(curState).getTexture().dispose();
        }
        else if (type == ResourceType.WOOD) {
           woodStateAttributeEnumMap.get(curState).getTexture().dispose();
        }
        //debugResourceTexture.dispose();
    }
}
