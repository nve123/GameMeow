package ru.education.tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import ru.education.MeowGame;

public class Core {
    protected final float x;
    protected final float y;
    protected final float width;
    protected final float height;
    protected  Texture texture;
    protected final Texture debugTexture;
    protected final Rectangle hitBox;
    private final Rectangle storageBox;

    public Core(float width, float height) {
        this.hitBox = new Rectangle(
            (MeowGame.SCREEN_WIDTH - width * 2) + width - 30,
            (MeowGame.SCREEN_HEIGHT / 2f - height / 4) + height / 5,
            25,
            25
        );
        this.texture = new Texture(Gdx.files.internal("coretower.png"));
        this.x = MeowGame.SCREEN_WIDTH - width * 2;
        this.y = MeowGame.SCREEN_HEIGHT / 2f - height / 4;
        this.width = width;
        this.height = height;
        debugTexture = new Texture(Gdx.files.internal("tmp.png"));
        storageBox = new Rectangle(x - 10, y + height / 6, 25, 25);
    }



    public Rectangle getStorageBox() {
        return storageBox;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);

        //Отладочная информация
        batch.draw(debugTexture, hitBox.x, hitBox.y, hitBox.width, hitBox.height);
        batch.draw(debugTexture, storageBox.x, storageBox.y, storageBox.width, storageBox.height);
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

    public void dispose() {
        texture.dispose();
        debugTexture.dispose();
    }
}
