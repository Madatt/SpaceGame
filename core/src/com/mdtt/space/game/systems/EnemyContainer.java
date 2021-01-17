package com.mdtt.space.game.systems;

import com.mdtt.space.Assets;
import com.mdtt.space.entities.enemies.Enemy;
import com.mdtt.space.screens.SpaceGameScreen;

import java.util.ArrayList;

public class EnemyContainer extends GameSystem {
    private ArrayList<Enemy> enemies = new ArrayList<>();

    public EnemyContainer(Assets assets, SpaceGameScreen screen) {
        super(assets, screen);
    }

    @Override
    public void step(float delta) {
        for(Enemy e : new ArrayList<>(enemies)) {
            if(e.isDead()) {
                enemies.remove(e);
            }
        }
    }

    public boolean allDead() {
        for(Enemy e : enemies) {
            if(!e.isDead()) {
                return false;
            }
        }
        return true;
    }

    public void add(Enemy enemy) {
        enemies.add(enemy);
    }
}
