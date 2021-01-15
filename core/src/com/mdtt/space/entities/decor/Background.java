package com.mdtt.space.entities.decor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.mdtt.space.Assets;
import com.mdtt.space.entities.common.Entity;
import com.mdtt.space.screens.CommonScreen;
import com.mdtt.space.screens.SpaceGameScreen;

public class Background extends Entity {
    public static float speed = 600f;

    private Sprite sprite;
    private int tick = 0;

    public Background(Assets assets, SpaceGameScreen screen) {
        super(assets, screen);

        sprite = assets.getSprite("Background");
    }

    @Override
    public Rectangle getBox() {
        return null;
    }

    @Override
    public void entityStep(float delta) {
        tick += delta * speed;
    }

    @Override
    public void draw(Batch batch) {
        sprite.setPosition(0, -tick % CommonScreen.height);
        sprite.draw(batch);
        sprite.setPosition(0, -tick % CommonScreen.height + CommonScreen.height);
        sprite.draw(batch);
    }

    @Override
    public void die() {

    }
}
