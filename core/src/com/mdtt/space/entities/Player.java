package com.mdtt.space.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import com.mdtt.space.Assets;
import com.mdtt.space.SpaceGame;
import com.mdtt.space.entities.common.Ship;
import com.mdtt.space.game.EntityFactory;
import com.mdtt.space.game.GameUtils;
import com.mdtt.space.screens.SpaceGameScreen;

public class Player extends Ship {
    public static float playerBaseDamage = 10.f;
    public static float playerCollisionScale = 0.7f;
    public static String playerBulletSprite = "Laser_Small";
    public static float playerBulletSpeed = 950.f;
    public static long playerInvincibility = 1800;
    public static long playerInvincibilityAnim = 800;


    private Sprite[] sprites;
    public int mode = 0;
    private com.mdtt.space.game.EntityFactory factory;

    private boolean vulnerable = true;
    private float vulnerableAnim = 1.f;
    private long vulnerableTimer = 0;

    public Player(Assets assets, SpaceGameScreen screen) {
        super(assets, screen);
        health = SpaceGame.playerHealth;
        sprites = new Sprite[] {
                SpaceGame.getPlayerSkin()
        };

        factory = new EntityFactory(assets, screen);
    }

    @Override
    public Rectangle getBox() {
        return new Rectangle(x - sprites[mode].getOriginX() , y - sprites[mode].getOriginY(),
                sprites[mode].getWidth(), sprites[mode].getHeight());
    }

    @Override
    public Rectangle getCollisionBox() {
        return new Rectangle(x - sprites[mode].getOriginX() * playerCollisionScale,
                y - sprites[mode].getOriginY() * playerCollisionScale,
                sprites[mode].getWidth() * playerCollisionScale,
                sprites[mode].getHeight() * playerCollisionScale);
    }

    @Override
    public void entityStep(float delta) {
        if(!vulnerable) {
            vulnerableAnim = GameUtils.pingPong(0.2f, 1.f, playerInvincibilityAnim);
        } else {
            vulnerableAnim = 1.f;
        }
    }

    public void draw(Batch batch) {
        if(!vulnerable && TimeUtils.timeSinceMillis(vulnerableTimer) >= playerInvincibility) {
            vulnerable = true;
        }

        sprites[mode].setColor(1, 1, 1, vulnerableAnim);
        sprites[mode].setPosition(x - sprites[mode].getOriginX(),
                y - sprites[mode].getOriginY());
        sprites[mode].draw(batch);
    }

    @Override
    public void die() {
        factory.createExplosion(this,20);
    }

    @Override
    public void damage(float damage) {
        if(!vulnerable)
            return;

        health -= damage;
        vulnerable = false;
        vulnerableTimer = TimeUtils.millis();
        if(health <= 0) {
            factory.createExplosion(this, 3);
            kill();
        }
    }
}
