package com.mdtt.space.entities.common;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.mdtt.space.Assets;
import com.mdtt.space.entities.common.Ship;
import com.mdtt.space.game.EntityFactory;
import com.mdtt.space.screens.SpaceGameScreen;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Bullet extends Ship {
    private Sprite sprite;
    public float speed = 800.f;
    public float direction = 0;
    public boolean enemy;
    public float damage = 1.f;
    private EntityFactory factory;

    public Bullet(Assets assets, SpaceGameScreen screen,
                  float direction, float speed, float damage, boolean enemy, String sprite) {
        super(assets, screen);
        this.sprite = assets.getSprite(sprite);
        this.direction = direction;
        this.enemy = enemy;
        this.damage = damage;
        this.speed = speed;
        this.factory = new EntityFactory(assets, screen);
    }

    @Override
    public Rectangle getBox() {
        return new Rectangle(x, y, sprite.getWidth(), sprite.getHeight());
    }

    @Override
    public void entityStep(float delta) {
        y = y + delta * speed * (float)sin(Math.toRadians(direction));
        x = x + delta * speed * (float)cos(Math.toRadians(direction));
    }

    @Override
    public void draw(Batch batch) {
        sprite.setOriginCenter();
        sprite.setPosition(x - sprite.getOriginX(),
                y - sprite.getOriginY());
        sprite.setRotation(direction - 90.f);
        sprite.draw(batch);
    }

    @Override
    public void die() {

    }

    @Override
    public void damage(float damage) {
        factory.createExplosion(this,1, 0.5f);
        kill();
    }
}
