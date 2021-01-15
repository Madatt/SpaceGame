package com.mdtt.space.entities.enemies;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import com.mdtt.space.Assets;
import com.mdtt.space.game.EntityFactory;
import com.mdtt.space.screens.SpaceGameScreen;

public class BlueBasic extends Enemy {
    public static final int blueBasicHealth = 150;
    public static final float blueBasicDamage = 25.f;
    public static final String blueBasicBulletSprite = "Plasma_Medium";
    public static float blueBasicBulletSpeed = 300.f;
    public static long blueBasicBulletRate = 1000;

    private Sprite sprite;
    private long timer = 0;
    public BlueBasic(Assets assets, SpaceGameScreen screen) {
        super(assets, screen);
        sprite = assets.getSprite("Enemy02_Blue_Frame", 1);
        health = blueBasicHealth;

        score = 1000;
        timer = TimeUtils.millis();
    }

    @Override
    public void entityStep(float delta) {
        if(TimeUtils.timeSinceMillis(timer) >= blueBasicBulletRate) {
            timer = TimeUtils.millis();
            factory.createBullet(x - 16, y, -90, com.mdtt.space.game.EntityFactory.BulletType.BlueBasic);
            factory.createBullet(x + 16, y, -90, EntityFactory.BulletType.BlueBasic);
        }

    }

    @Override
    public Rectangle getBox() {
        return new Rectangle(x - sprite.getOriginX(), y - sprite.getOriginY(),
                sprite.getWidth(), sprite.getHeight());
    }

    @Override
    public void draw(Batch batch) {
        sprite.setOriginCenter();
        sprite.setPosition(x - sprite.getOriginX(), y - sprite.getOriginY());
        sprite.draw(batch);
    }

    @Override
    public void die() {
        factory.createExplosion(this, 10);
    }
}
