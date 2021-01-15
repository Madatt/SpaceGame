package com.mdtt.space.entities.common;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import com.mdtt.space.Assets;
import com.mdtt.space.game.systems.animators.Waypoint;
import com.mdtt.space.screens.SpaceGameScreen;

import java.util.ArrayList;

import static com.badlogic.gdx.math.MathUtils.lerp;

public abstract class Entity {
    protected Assets assets;
    public float x, y, lastX, lastY;
    public static final float inter = 7.f;
    protected boolean dead = false;
    protected SpaceGameScreen screen;
    private ArrayList<Waypoint> waypoints = new ArrayList<>();

    private int waypointCurrent = 0;
    private boolean waypointLoop = false;
    private boolean waypointStart = false;
    private long waypointTimer = 0;
    private boolean active = true;

    public Entity(Assets assets, SpaceGameScreen screen) {
        this.assets = assets;
        this.screen = screen;
    }

    public void addWaypoint(float x, float y, long delay) {
        waypoints.add(new Waypoint(x, y, delay));
    }

    public void addWaypoint(double x, double y, long delay) {
        waypoints.add(new Waypoint((float) x, (float) y, delay));
    }

    public void loop() {
        waypointLoop = !waypointLoop;
    }

    public void toggle() {
        active = !active;
    }

    public void start() {
        waypointStart = true;
        waypointTimer = TimeUtils.millis();
    }

    public void startInactive() {
        start();
        active = false;
    }

    public void stop() {
        waypointStart = false;
    }

    public void lerpX(float x, float delta) {
        lastX = this.x;
        this.x = lerp(this.x, x, delta * inter);
    }

    public void lerpY(float y, float delta) {
        lastY = this.y;
        this.y = lerp(this.y, y, delta * inter);
    }

    public void kill() {
        dead = true;
        die();
    }

    public boolean touches(Entity entity) {
        return getCollisionBox().overlaps(entity.getCollisionBox());
    }

    public abstract Rectangle getBox();

    public Rectangle getCollisionBox() {
        return getBox();
    }

    public final void step(float delta) {
        if (waypointStart) {
            Waypoint waypoint = waypoints.get(waypointCurrent);
            float progress = TimeUtils.timeSinceMillis(waypointTimer) / (float) waypoint.delay;

            x = MathUtils.lerp(x, waypoint.x, progress);
            y = MathUtils.lerp(y, waypoint.y, progress);

            if (TimeUtils.timeSinceMillis(waypointTimer) >= waypoints.get(waypointCurrent).delay) {
                waypointTimer = TimeUtils.millis();
                waypointCurrent += 1;
                if (waypointCurrent >= waypoints.size()) {
                    if (!waypointLoop) {
                        waypointCurrent = 0;
                        stop();
                    } else {
                        waypointCurrent = 0;
                    }
                    active = true;
                }
            }
        }

        if (active)
            entityStep(delta);
    }

    public abstract void entityStep(float delta);

    public abstract void draw(Batch batch);

    public abstract void die();

    public boolean isDead() {
        return dead;
    }
}
