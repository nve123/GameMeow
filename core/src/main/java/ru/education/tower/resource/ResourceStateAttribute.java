package ru.education.tower.resource;

import com.badlogic.gdx.graphics.Texture;

public class ResourceStateAttribute {
    private final Texture texture;
    private final float width;
    private final float height;

    public ResourceStateAttribute (Texture texture, float width, float height){
        this.texture = texture;
        this.width = width;
        this.height = height;
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
}
