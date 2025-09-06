package ru.education.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class AnimationUtil {
    private AnimationUtil() {
    }

    public static Animation<TextureRegion> getAnimationFromAtlas(
        TextureAtlas atlas,
        float timeAnimation
    ) {

        Array<TextureAtlas.AtlasRegion> regions = atlas.getRegions();

        return new Animation<TextureRegion>(
            timeAnimation / regions.size,
            regions);
    }

    public static Animation<TextureRegion> getAnimationFromTexture(
        Texture texture,
        int numberOfTileWidth,
        int numberOfTileHeight,
        float timeAnimation
    ) {

        TextureRegion[][] split = TextureRegion.split(
            texture,
            texture.getWidth() / numberOfTileWidth,
            texture.getHeight() / numberOfTileHeight
        );

        int numberOfFrame = numberOfTileWidth * numberOfTileHeight;

        TextureRegion[] frameArray = new TextureRegion[numberOfFrame];
        int i = 0;
        for (int j = 0; j < numberOfTileHeight; j++) {
            for (int k = 0; k < numberOfTileWidth; k++) {
                frameArray[i] = split[j][k];
                i++;
            }
        }

        return new Animation<>(timeAnimation / numberOfFrame, frameArray);
    }

}
