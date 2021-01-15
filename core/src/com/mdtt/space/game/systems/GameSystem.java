package com.mdtt.space.game.systems;

import com.mdtt.space.Assets;
import com.mdtt.space.screens.SpaceGameScreen;

public abstract class GameSystem {
    protected Assets assets;
    protected SpaceGameScreen screen;

    public GameSystem(Assets assets, SpaceGameScreen screen) {
        this.assets = assets;
        this.screen = screen;
    }

    public abstract void step(float delta);
}
