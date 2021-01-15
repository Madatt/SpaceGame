package com.mdtt.space.game.systems.animators;

public class Waypoint {
    public float x = 0;
    public float y = 0;
    public long delay = 0;

    public Waypoint(float x, float y, long delay) {
        this.x = x;
        this.y = y;
        this.delay = delay;
    }
}
