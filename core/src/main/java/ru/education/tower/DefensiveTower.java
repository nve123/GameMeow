package ru.education.tower;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class DefensiveTower extends Tower {

    public DefensiveTower(
        float x, float y,
        float width, float height,
        Texture texture
    ) {
        super(x, y, width, height, texture, new Rectangle(x, y, width, height));
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
    }
}
