package ru.education.service;

import com.badlogic.gdx.utils.Array;

import ru.education.wave.Wave;

public class WaveService {
    private Array<Wave> waves;
    private Wave curWave;
    private int curNumberWave;

    public WaveService(Array<Wave> waves) {
        this.waves = waves;
        this.curNumberWave = 0;
        this.curWave = waves.get(curNumberWave);
    }

    public Wave nextWave() {
        curNumberWave++;
        curWave = waves.get(curNumberWave);

        return curWave;
    }

    public int getCurNumberWave() {
        return curNumberWave;
    }

    public Wave getCurWave() {
        return curWave;
    }

    public void dispose(){
        for (Wave wave : waves) {
            wave.dispose();
        }
    }

}
