package ru.education.tower;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import ru.education.util.AnimationUtil;
import ru.education.unit.Enemy;

public class DefensiveTower extends Tower {
    private float lastShot;
    private final Rectangle range;
    private final Array<Shot> shotArray;

    public DefensiveTower(
        float x, float y,
        float width, float height,
        Texture texture
    ) {
        super(x, y, width, height, texture, new Rectangle(x, y, width, height));

        shotArray = new Array<>();
        range = new Rectangle(x, y - 200f, hitBox.width, 200f + hitBox.height + 200f);
        lastShot = -1;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
    }

    public void draw(SpriteBatch batch, Enemy enemy, float curTime) {
        batch.draw(texture, x, y, width, height);
        batch.draw(new Texture("tmp.png"), range.x, range.y, range.width, range.height);
        if (curTime - lastShot > 5 && range.contains(enemy.getX(), enemy.getY()) && enemy.isAlive()) {
            lastShot = curTime;
            shotArray.add(new Shot(enemy));
        }
        for (Shot shot : shotArray) {
            if (!shot.inTarget) {
                batch.draw(shot.getCurrentFrame(curTime), shot.x, shot.y);
                shot.nextXY();
            }
        }
    }

    public class Shot {
        private boolean inTarget;
        private Rectangle target;

        private final float width;
        private final float height;
        private float deltaX;
        private float deltaY;
        private float x, y;
        private final Animation<TextureRegion> animations;
        private final Enemy enemy;

        public Shot(Enemy enemy) {
            this.enemy = enemy;
            this.x = DefensiveTower.this.x + DefensiveTower.this.width / 2f;
            this.y = DefensiveTower.this.y + DefensiveTower.this.height / 2f;
            animations = AnimationUtil.getAnimationFromTexture(
                new Texture("spark16.png"),
                5,
                1,
                0.5f
            );
            width = new Texture("spark16.png").getWidth() / 5f;
            height = new Texture("spark16.png").getHeight();
            setDestination(
                enemy.getX() + enemy.getWidth() / 4f + enemy.getDeltaX() * 60,
                enemy.getY() + enemy.getHeight() / 2f + enemy.getDeltaY() * 60
            );

        }

        public void setDestination(float destinationX, float destinationY) {
            target = new Rectangle(destinationX - 12f, destinationY - 12f, 24f, 24f);
            calcDeltaXAndDeltaY();
        }

        private void calcDeltaXAndDeltaY() {
            float s = (float) Math.sqrt(
                (x - (target.x + target.width / 2f)) * (x - (target.x + target.width / 2f)) +
                    (y - (target.y + target.height / 2f)) * (y - (target.y + target.height / 2f))
            );
            float deltaS = s / 60f;
            deltaX = (deltaS * Math.abs(x - (target.x + target.width / 2f))) / s;
            deltaY = (deltaS * Math.abs(y - (target.y + target.height / 2f))) / s;
        }

        public void nextXY() {

            //TODO: сделать проверку на пересечение хитбоксов, а не на левый угол
            if (!target.contains(x, y)) {

                if (target.y + target.height / 2f > y) y += deltaY;
                if (target.y + target.height / 2f < y) y -= deltaY;

                if (target.x + target.width / 2f >= x) {
                    x += deltaX;
                }
                if (target.x + target.width / 2f < x) {
                    x -= deltaX;
                }

            } else {
                Rectangle enemyHitBox = new Rectangle(
                    enemy.getX(), enemy.getY(),
                    enemy.getWidth(),
                    enemy.getHeight()
                );
                if (enemyHitBox.contains(x, y))
                    enemy.getDmg(2);
                inTarget = true;
            }

        }

        public TextureRegion getCurrentFrame(float time) {
            return animations.getKeyFrame(time, true);
        }
    }
}
