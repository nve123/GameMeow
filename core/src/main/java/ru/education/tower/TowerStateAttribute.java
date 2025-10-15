package ru.education.tower;

import com.badlogic.gdx.graphics.Texture;

public class TowerStateAttribute {
    private Texture texture;
    private float width;
    private float height;
    private int dmg;
    private int attackSpeed;

    public TowerStateAttribute(Texture texture, float width, float height, int dmg, int attackSpeed) {
        this.texture = texture;
        this.width = width;
        this.height = height;
        this.dmg = dmg;
        this.attackSpeed = attackSpeed;
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
}
