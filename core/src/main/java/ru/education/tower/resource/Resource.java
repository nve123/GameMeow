package ru.education.tower.resource;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Resource {
    private final Rectangle hitBox;
    private final Texture texture;
    private final ResourceType type;
    private final float x;
    private final float y;
    private final float width;
    private final float height;
    private final Rectangle workBox;

    public Resource(float x, float y, Texture texture, ResourceType type, float width, float height) {
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.type = type;
        this.width = width;
        this.height = height;
        hitBox = new Rectangle(x, y, this.width, this.height);
        workBox = new Rectangle(x + width - 20, y, 15f, 15f);
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
        batch.draw(texture, x, y, width, height);

        //Отладочная информация
        batch.draw(new Texture(Gdx.files.internal("tmp.png")), workBox.x, workBox.y, workBox.width, workBox.height);
        //batch.draw(new Texture(Gdx.files.internal("tmp.png")), hitBox.x, hitBox.y, hitBox.width, hitBox.height);
    }

    public void dispose() {
        texture.dispose();
    }
}
