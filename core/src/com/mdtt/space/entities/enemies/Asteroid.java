package com.mdtt.space.entities.enemies;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.mdtt.space.Assets;
import com.mdtt.space.screens.SpaceGameScreen;

import java.util.Random;

public class Asteroid extends Enemy {
    public static float flySpeed = 100.f;
    public static float spinRate = 20.f;
    public static long respawnMin = 1000;
    public static long respawnMax = 3000;
    public static float damage = 25.f;

    public static String[] sprites = {
            "Asteroid 01",
            "Asteroid 02",
            "Asteroid 03",
            "Asteroid 04"
    };

    private Sprite sprite;
    private float direction = 90f;
    private float spin = 0;
    private float speed = flySpeed;

    public Asteroid(Assets assets, SpaceGameScreen screen) {
        super(assets, screen);
        Random rand = new Random();
        direction += rand.nextInt(20) - 10;
        speed += rand.nextInt(20);
        sprite = assets.getSprite(sprites[rand.nextInt(3)]);

        score = 10;
    }

    @Override
    public void damage(float damage) {
        kill();
    }

    @Override
    public Rectangle getBox() {
        return new Rectangle(x - sprite.getOriginX() , y - sprite.getOriginY(),
                sprite.getWidth(), sprite.getHeight());
    }

    @Override
    public void entityStep(float delta) {
        spin += spinRate * delta;
        x += speed * Math.cos(Math.toRadians(direction)) * delta;
        y -= speed * Math.sin(Math.toRadians(direction)) * delta;
    }

    @Override
    public void draw(Batch batch) {
        sprite.setOriginCenter();
        sprite.setPosition(x - sprite.getOriginX(), y - sprite.getOriginY());
        sprite.setRotation(spin);
        sprite.draw(batch);
    }

    @Override
    public void die() {
        factory.createExplosion(this, 1);
    }
}
