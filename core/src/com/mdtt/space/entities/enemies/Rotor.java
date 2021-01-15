package com.mdtt.space.entities.enemies;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import com.mdtt.space.Assets;
import com.mdtt.space.game.EntityFactory;
import com.mdtt.space.game.GameTimer;
import com.mdtt.space.screens.SpaceGameScreen;

import static com.badlogic.gdx.math.MathUtils.lerp;

public class Rotor extends Enemy {
    public static final int rotorHealth = 20;
    public static final float rotorDamage = 10.f;
    public static final String rotorBulletSprite = "Laser_Small";
    public static float rotorBulletSpeed = 150.f;

    public static final float spinRate = 100.f;
    public static final long shootRate = 3000;
    public static final int bullets = 6;

    private Sprite sprite;
    private Sprite spriteCover;
    private float spin = 0;
    //private long timer;
    private GameTimer timer;
    private float scale = 1.f;

    public Rotor(Assets assets, SpaceGameScreen screen) {
        super(assets, screen);

        sprite = assets.getSprite("Rotor");
        spriteCover = assets.getSprite("Cover_Blue");
        timer = new GameTimer();
        timer.restart(shootRate);
        //timer = TimeUtils.millis();
        health = rotorHealth;

        score = 100;
    }

    @Override
    public Rectangle getBox() {
        return new Rectangle(x - sprite.getOriginX() * scale, y - sprite.getOriginY() * scale,
                sprite.getWidth() * scale, sprite.getHeight() * scale);
    }

    @Override
    public void entityStep(float delta) {
        spin += delta * spinRate;
        if (timer.elapsed()) {
            timer.restart(shootRate);
            for (int i = 1; i <= bullets; i++) {
                factory.createBullet(x, y, 360.f / bullets * i + spin,
                        EntityFactory.BulletType.RotorBase);
            }
        }
    }

    @Override
    public void draw(Batch batch) {
        scale = lerp(0.7f, 1.f, timer.current() / (float)shootRate);

        sprite.setOriginCenter();
        sprite.setPosition(x - sprite.getOriginX(), y - sprite.getOriginY());
        sprite.setRotation(spin);
        sprite.setScale(scale);
        sprite.draw(batch);


        spriteCover.setOriginCenter();
        spriteCover.setRotation(-spin);
        spriteCover.setPosition(x - spriteCover.getOriginX(), y - spriteCover.getOriginY());
        spriteCover.draw(batch);
    }

    @Override
    public void die() {
        factory.createExplosion(this, 3);
    }
}
