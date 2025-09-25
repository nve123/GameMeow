package ru.education.tower;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Gdx;

public abstract class Tower {
    protected final float x;
    protected final float y;
    protected final float width;
    protected final float height;
    protected final Texture texture;
    protected final Texture debugTexture;
    protected final Rectangle hitBox;

    public Tower(
        float x, float y,
        float width, float height,
        Texture texture,
        Rectangle hitBox
    ) {
        this.hitBox = hitBox;
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        debugTexture = new Texture(Gdx.files.internal("tmp.png"));
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

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public abstract void draw(SpriteBatch batch);

    public void dispose() {
        texture.dispose();
        debugTexture.dispose();
    }

}
