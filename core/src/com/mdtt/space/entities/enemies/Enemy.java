package com.mdtt.space.entities.enemies;

import com.mdtt.space.Assets;
import com.mdtt.space.entities.common.Ship;
import com.mdtt.space.screens.SpaceGameScreen;

public abstract class Enemy extends Ship {
    public int score = 0;

    public Enemy(Assets assets, SpaceGameScreen screen) {
        super(assets, screen);
    }



}
