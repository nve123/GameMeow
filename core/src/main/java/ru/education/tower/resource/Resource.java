package ru.education.tower.resource;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Resource {
    private final Rectangle hitBox;
    private Texture texture;
    private final ResourceType type;
    private final float x;
    private final float y;
    private float width;
    private float height;
    private Rectangle workBox;
    private Texture debugResourceTexture;

    public Resource(float x, float y, ResourceType type, Rectangle workBox) {
        switch (type) {
            case ORE -> {
                texture = new Texture(Gdx.files.internal("ore.png"));
                width = 146;
                height = 119;
            }
            case GOLD -> {
                texture = new Texture(Gdx.files.internal("gold.png"));
                width = 170;
                height = 119;
            }
            case WOOD -> {
                texture = new Texture(Gdx.files.internal("wood.png"));
                width = 150;
                height = 125;
            }
        }
        this.x = x;
        this.y = y;
        this.type = type;
        this.debugResourceTexture = new Texture(Gdx.files.internal("tmp.png"));
        hitBox = new Rectangle(x, y, this.width, this.height);
        this.workBox = workBox;
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

        batch.draw(debugResourceTexture, workBox.x, workBox.y, workBox.width, workBox.height);
        //batch.draw(new Texture(Gdx.files.internal("tmp.png")), hitBox.x, hitBox.y, hitBox.width, hitBox.height);
    }

    public void dispose() {
        texture.dispose();
        debugResourceTexture.dispose();
    }
}
