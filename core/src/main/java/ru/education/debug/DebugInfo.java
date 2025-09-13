package ru.education.debug;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import ru.education.MeowGame;

public class DebugInfo implements Disposable {
    public static final int MAX_NUMBER_DEBUG_LINE = 20;
    private final BitmapFont font;
    private final StringBuilder stringBuilder;
    private int lineCounter;

    public DebugInfo() {
        this.stringBuilder = new StringBuilder();
        font = new BitmapFont();
        font.getData().setScale(0.5f);
        lineCounter = 0;
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
        if (lineCounter > MAX_NUMBER_DEBUG_LINE) {
            stringBuilder.setLength(0);
            lineCounter = 0;
        }
        stringBuilder.append(str).append('\n');
        lineCounter++;
    }

    @Override
    public void dispose() {
        font.dispose();
    }
}
