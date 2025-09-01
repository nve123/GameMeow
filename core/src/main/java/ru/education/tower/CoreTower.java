package ru.education.tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import ru.education.MeowGame;

public class CoreTower {
    private final Rectangle hitBox;
    private final Rectangle storageBox;
    private final Texture texture;
    private final float x;
    private final float y;
    private final float width;
    private final float height;

    public CoreTower(float width, float height) {
        this.width = width;
        this.height = height;
        texture = new Texture(Gdx.files.internal("coretower.png"));
        x = MeowGame.SCREEN_WIDTH - width * 2;
        y = MeowGame.SCREEN_HEIGHT / 2f - height / 4;
        hitBox = new Rectangle(x + width - 30, y + height / 5, 25, 25);
        storageBox = new Rectangle(x - 10, y + height / 6, 25, 25);
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

    public Rectangle getStorageBox() {
        return storageBox;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getY() {
        return y;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);

        //Отладочная информация
        batch.draw(new Texture(Gdx.files.internal("tmp.png")), hitBox.x, hitBox.y, hitBox.width, hitBox.height);
        batch.draw(new Texture(Gdx.files.internal("tmp.png")), storageBox.x, storageBox.y, storageBox.width, storageBox.height);
    }
}
