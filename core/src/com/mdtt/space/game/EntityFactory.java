package com.mdtt.space.game;

import com.mdtt.space.Assets;
import com.mdtt.space.entities.Player;
import com.mdtt.space.entities.common.Bullet;
import com.mdtt.space.entities.common.Ship;
import com.mdtt.space.entities.decor.Explosion;
import com.mdtt.space.entities.enemies.Asteroid;
import com.mdtt.space.entities.enemies.BlueBasic;
import com.mdtt.space.entities.enemies.Rotor;
import com.mdtt.space.screens.SpaceGameScreen;

import java.util.Random;

public class EntityFactory {
    public static enum BulletType {
        PlayerBase,
        RotorBase,
        BlueBasic,
    }

    private Assets assets;
    private SpaceGameScreen screen;

    public EntityFactory(Assets assets, SpaceGameScreen screen) {
        this.assets = assets;
        this.screen = screen;
    }

    public void createExplosion(Ship ship, int n) {
        createExplosion(ship, n, 1.f);
    }

    public void createExplosion(Ship ship, int n, float scale) {
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            float del = i == 0 ? 0 : rand.nextFloat() * 0.7f;
            Explosion explosion = new Explosion(assets, screen, del, scale);
            explosion.x = ship.x + rand.nextInt((int) (ship.getBox().width / 2.5));
            explosion.y = ship.y + rand.nextInt((int) (ship.getBox().height / 2.5));
            screen.addEntity(explosion);
        }
    }

    public Rotor createRotor(float x, float y) {
        Rotor rotor = new Rotor(assets, screen);
        rotor.x = x;
        rotor.y = y;
        screen.addEntity(rotor);
        return rotor;
    }

    public BlueBasic createBlueBasic(float x, float y) {
        BlueBasic blue = new BlueBasic(assets, screen);
        blue.x = x;
        blue.y = y;
        screen.addEntity(blue);
        return blue;
    }

    public Asteroid createAsteroid(float x, float y) {
        Asteroid asteroid = new Asteroid(assets, screen);
        asteroid.x = x;
        asteroid.y = y;
        screen.addEntity(asteroid);
        return asteroid;
    }

    public Bullet createBullet(float x, float y, float direction, BulletType type) {
        switch (type) {
            case PlayerBase: {
                Bullet bullet = new Bullet(assets, screen, direction, Player.playerBulletSpeed,
                        Player.playerBaseDamage, false, Player.playerBulletSprite);
                bullet.x = x;
                bullet.y = y;
                screen.addEntity(bullet);
                return bullet;
            }

            case RotorBase: {
                Bullet bullet = new Bullet(assets, screen, direction, Rotor.rotorBulletSpeed,
                        Rotor.rotorDamage, true, Rotor.rotorBulletSprite);
                bullet.x = x;
                bullet.y = y;
                screen.addEntity(bullet);
                return bullet;
            }

            case BlueBasic: {
                Bullet bullet = new Bullet(assets, screen, direction, BlueBasic.blueBasicBulletSpeed,
                        BlueBasic.blueBasicDamage, true, BlueBasic.blueBasicBulletSprite);
                bullet.x = x;
                bullet.y = y;
                screen.addEntity(bullet);
                return bullet;
            }
        }

        return null;
    }
}
