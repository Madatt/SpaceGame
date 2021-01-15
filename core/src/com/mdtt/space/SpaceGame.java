package com.mdtt.space;

import com.badlogic.gdx.Game;
import com.mdtt.space.screens.MenuScreen;

public class SpaceGame extends Game {
	Assets assets;
	
	@Override
	public void create () {
		assets = new Assets();
		assets.load();
		setScreen(new MenuScreen(this, assets));
	}

}
