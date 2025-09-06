package ru.education.tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class SlotTower extends Tower {

    private boolean isFree;
    private boolean isVisible;

    public SlotTower(float x, float y) {
        super(
            x, y,
            100, 100,
            new Texture(Gdx.files.internal("tmp.png")),
            new Rectangle(x, y, 100, 100));
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

    @Override
    public void draw(SpriteBatch batch) {
        if (isFree && isVisible) batch.draw(texture, x, y, width, height);
    }
}
