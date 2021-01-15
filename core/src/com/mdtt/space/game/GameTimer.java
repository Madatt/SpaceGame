package com.mdtt.space.game;

import com.badlogic.gdx.utils.TimeUtils;

public class GameTimer {
    private long timer = 0;
    private boolean start = false;
    private long time = 0;

    public GameTimer() {
    }

    public void restart(long time) {
        this.time = time;
        timer = TimeUtils.millis();
        start = true;
    }

    public void stop() {
        if(!start) {
            return;
        }
        start = false;
    }

    public boolean running() {
        return start;
    }

    public long current() {
        return TimeUtils.timeSinceMillis(timer);
    }

    public boolean elapsed() {
        if(!start)
            return false;

        if(TimeUtils.timeSinceMillis(timer) >= time) {
            start = false;
            return true;
        }
        return false;
    }
}
