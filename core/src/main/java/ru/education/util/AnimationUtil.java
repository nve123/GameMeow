package ru.education.util;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class AnimationUtil {

    private AnimationUtil() {}

    public static Animation<TextureRegion> getAnimationFromAtlas (
        String atlasPath,
        float timeAnimation
    ) {
        TextureAtlas atlas = new TextureAtlas(atlasPath);
        Array<TextureAtlas.AtlasRegion> regions = atlas.getRegions();

        Animation<TextureRegion> textureRegionAnimation = new Animation<TextureRegion>(
            timeAnimation / regions.size,
            regions
        );

        return textureRegionAnimation;
    }
}
