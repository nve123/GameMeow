package ru.education.wave;

import com.badlogic.gdx.utils.Array;

import ru.education.unit.Enemy;

public class Wave {
    private Array<Enemy> enemies;
    private boolean isAliveWave;

    public Wave(Array<Enemy> wave) {
        this.enemies = wave;
        isAliveWave = true;
    }

    public boolean isAliveWave() {
        int count = 0;
        for (int i = 0; i < enemies.size; i++) {
            if (!enemies.get(i).isAlive()) {
                count++;
                isAliveWave = true;
            }
        }
        if (count == enemies.size) {
            isAliveWave = false;        }
        return isAliveWave;

    }

    public void reviveWave() {
        isAliveWave = true;
        for (int i = 0; i < enemies.size; i++) {
            if (!enemies.get(i).isAlive()) {
                enemies.get(i).reviveEnemy();
            }
        }
    }

    public Array<Enemy> getEnemies() {
        return enemies;
    }

    public void dispose() {
        for (Enemy enemy : enemies) {
            enemy.dispose();
        }
    }
}
