package com.mdtt.space;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mdtt.space.screens.MenuScreen;

public class SpaceGame extends Game {
    public static int playerSkin = 0;
    public static int playerHealth = 100;

    public static Assets assets;

    @Override
    public void create() {
        assets = new Assets();
        assets.load();
        setScreen(new MenuScreen(this, assets));
    }

    static public Sprite getPlayerSkin() {
        if (playerSkin == 0) {
            return assets.getSprite("PlayerRed_Frame", 1);
		} else if (playerSkin == 1) {
			return assets.getSprite("PlayerBlue_Frame", 1);
		}
        return null;
    }

    static public void changePlayerSkin() {
    	playerSkin = (playerSkin + 1) % 2;
	}

	static public void changePlayerHealth() {
        playerHealth = (playerHealth + 50) % 300;
        if(playerHealth == 0)
            playerHealth = 100;
    }

}
