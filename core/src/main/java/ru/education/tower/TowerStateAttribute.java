package ru.education.tower;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class TowerStateAttribute {
    private final Texture texture;
    private final float width;
    private final float height;
    private final int dmg;
    private final int attackSpeed;
    private final Rectangle hitBox;

    public TowerStateAttribute(Texture texture, float width, float height, int dmg, int attackSpeed, Rectangle hitBox) {
        this.texture = texture;
        this.width = width;
        this.height = height;
        this.dmg = dmg;
        this.attackSpeed = attackSpeed;
        this.hitBox = hitBox;
    }

    public int getDmg() {
        return dmg;
    }

    public Texture getTexture() {
        return texture;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public int getAttackSpeed() {
        return attackSpeed;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }
}
