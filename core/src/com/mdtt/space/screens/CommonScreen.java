package com.mdtt.space.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mdtt.space.Assets;

public abstract class CommonScreen {
    public static final int width = 700;
    public static final int height = 1600;

    protected Assets assets;
    protected Sprite spriteBackground;
    protected SpriteBatch batch;
    protected Camera camera;
    protected Viewport viewport;
    protected final Game game;


    CommonScreen(Game game, Assets assets) {
        this.game = game;
        this.assets = assets;
        spriteBackground = assets.getSprite("Background");

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(width, height, camera);
    }

    public boolean touched() {
        return Gdx.input.justTouched();
    }

    public boolean touched(Rectangle rectangle) {
        if (!Gdx.input.justTouched())
            return false;

        Vector3 touchPoint = new Vector3();
        camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0),
                viewport.getScreenX(), viewport.getScreenY(),
                viewport.getScreenWidth(), viewport.getScreenHeight());
        return rectangle.contains(touchPoint.x, touchPoint.y);
    }
}
