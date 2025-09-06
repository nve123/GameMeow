package ru.education.shop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
public class Item {
    private Rectangle hitBox;
    private final Texture texture;
    private float x;
    private float y;
    private final Price price;
    public Item(Texture texture, Price price) {
        this.texture = texture;
        this.price = price;
    }
    public Rectangle getHitBox() {
        return hitBox;
    }
    public void setSlot(float x, float y) {
        this.hitBox = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
        this.x = x;
        this.y = y;
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
    public Price getPrice() {
        return price;
    }
}
