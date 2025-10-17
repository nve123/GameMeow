package ru.education.tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class SlotTower {
    protected final float x;
    protected final float y;
    protected final float width;
    protected final float height;
    protected  Texture texture;
    protected final Rectangle hitBox;
    private boolean isFree;
    private boolean isVisible;

    public SlotTower(float x, float y) {
        hitBox = new Rectangle(x, y, 100, 100);
        this.x = x;
        this.y = y;
        width = 100;
        height = 100;
        texture = new Texture(Gdx.files.internal("tmp.png"));
        isFree = true;
        isVisible = false;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public boolean isFree() {
        return isFree;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Texture getTexture() {
        return texture;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public void draw(SpriteBatch batch) {
        if (isFree && isVisible) batch.draw(texture, x, y, width, height);
    }

    public void dispose(){
        texture.dispose();
    }
}
