package ru.education.debug;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ru.education.MeowGame;


public class DebugInfo {
    private final BitmapFont font;
    private final StringBuilder stringBuilder;

    public DebugInfo() {
        this.stringBuilder = new StringBuilder();
        font = new BitmapFont();
        font.getData().setScale(0.5f);
    }

    public void draw(SpriteBatch batch) {
        font.draw(
            batch,
            stringBuilder.toString(),
            0,
            MeowGame.SCREEN_HEIGHT
        );
    }

    public void addInfo(String str) {
        stringBuilder.append(str).append('\n');
    }

    public void dispose() {
        font.dispose();
    }
}
