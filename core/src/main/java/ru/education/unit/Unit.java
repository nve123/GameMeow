package ru.education.unit;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public abstract class Unit {
    protected boolean isAlive;
    protected float x;
    protected float y;
    protected float deltaX;
    protected float deltaY;
    protected Rectangle destination;
    protected float timeInState;

    protected static class StateAttribute {
        protected final float width;
        protected final float height;
        protected final Animation<TextureRegion> animation;
        protected final float speed;

        public StateAttribute(
            float width,
            float height,
            Animation<TextureRegion> animation,
            float speed
        ) {
            this.width = width;
            this.height = height;
            this.animation = animation;
            this.speed = speed;
        }
    }

    private void calcDeltaXAndDeltaY() {
        float s = (float) Math.sqrt(
            (x - (destination.x + destination.width / 2f)) * (x - (destination.x + destination.width / 2f)) +
                (y - (destination.y + destination.height / 2f)) * (y - (destination.y + destination.height / 2f))
        );
        float deltaS = getSpeed();
        deltaX = (deltaS * Math.abs(x - (destination.x + destination.width / 2f))) / s;
        deltaY = (deltaS * Math.abs(y - (destination.y + destination.height / 2f))) / s;
    }

    public void setDestination(float destinationX, float destinationY) {
        destination = new Rectangle(destinationX - 12f, destinationY - 12f, 24f, 24f);
        calcDeltaXAndDeltaY();
    }

    public void setDestination(Rectangle destination) {
        this.destination = destination;
        calcDeltaXAndDeltaY();
    }

    public abstract void initStateMap();

    public abstract float getSpeed();

    public abstract void draw(SpriteBatch batch);

    public abstract float getWidth();

    public abstract float getHeight();

    public abstract void nextXY();

}
