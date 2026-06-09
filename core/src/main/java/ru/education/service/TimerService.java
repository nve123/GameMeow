package ru.education.service;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import ru.education.unit.Enemy;

public class TimerService {
    private float curTime;
    private boolean isActive;

    public TimerService() {
        curTime = Enemy.TIME_TO_START;
        isActive = false;
    }

    public void tick(float deltaTime){
        if (isActive && curTime >= 0){
         curTime -= deltaTime;
        }
    }

    public void resetTime(){
        curTime = Enemy.TIME_TO_START;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public float getCurTime() {
        return curTime;
    }

    public boolean isActive() {
        return isActive;
    }

    public void draw(Batch batch, BitmapFont font, float x, float y){
        font.draw(batch, "Next wave: " + (int) curTime, x, y);
    }
}
