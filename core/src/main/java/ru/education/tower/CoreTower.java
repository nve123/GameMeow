package ru.education.tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import ru.education.MeowGame;

public class CoreTower extends Tower {
    private final Rectangle storageBox;

    public CoreTower(float width, float height) {
        super(
            MeowGame.SCREEN_WIDTH - width * 2,
            MeowGame.SCREEN_HEIGHT / 2f - height / 4,
            width, height,
            new Texture(Gdx.files.internal("coretower.png")),
            new Rectangle(
                (MeowGame.SCREEN_WIDTH - width * 2) + width - 30,
                (MeowGame.SCREEN_HEIGHT / 2f - height / 4) + height / 5,
                25,
                25
            )
        );
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
}
