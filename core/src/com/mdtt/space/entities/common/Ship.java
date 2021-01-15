package com.mdtt.space.entities.common;

import com.mdtt.space.Assets;
import com.mdtt.space.game.EntityFactory;
import com.mdtt.space.screens.SpaceGameScreen;

public abstract class Ship extends Entity {
    public int health = 100;
    protected EntityFactory factory;

    public Ship(Assets assets, SpaceGameScreen screen) {
        super(assets, screen);
        factory = new EntityFactory(assets, screen);
    }

    public void damage(float damage) {
        health -= damage;
        if (health <= 0) {
            kill();
        }
    };
}
