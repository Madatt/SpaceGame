package com.mdtt.space.entities.decor;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.mdtt.space.Assets;
import com.mdtt.space.entities.common.Entity;
import com.mdtt.space.screens.SpaceGameScreen;

public class Explosion extends Entity {
    public static float speed = 0.03f;
    private static int frames = 9;

    private Animation<TextureRegion> animation;
    private float state = 0;
    private float scale = 1.f;

    public Explosion(Assets assets, SpaceGameScreen screen, float delay, float scale) {
        super(assets, screen);
        state -= delay;
        this.scale = scale;
        animation = new Animation<TextureRegion>(speed,
                assets.getAtlas().findRegions("Explosion01_Frame"));
    }

    @Override
    public Rectangle getBox() {
        return new Rectangle(x, y,
                animation.getKeyFrame(state < 0 ? 0 : state).getRegionWidth(),
                animation.getKeyFrame(state < 0 ? 0 : state).getRegionHeight());
    }

    @Override
    public void entityStep(float delta) {
        state += delta;
        if (state >= frames * speed) {
            kill();
        }
    }

    @Override
    public void draw(Batch batch) {
        if (state > 0) {
            batch.draw(animation.getKeyFrame(state, true),
                    x - getBox().width * scale / 2,
                    y - getBox().height * scale / 2,
                    getBox().width * scale,
                    getBox().height * scale);
        }
    }

    @Override
    public void die() {
    }
}
