package com.mdtt.space.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.mdtt.space.Assets;
import com.mdtt.space.entities.decor.Text;

public class MenuScreen extends CommonScreen implements Screen {
    private GlyphLayout layoutGame = new GlyphLayout(assets.getFontBig(), "GAME");
    private GlyphLayout layoutPress = new GlyphLayout(assets.getFontMedium(), "Press anywhere");

    private Text textGame;
    private Text textTouch;
    private Text textOptions;

    public MenuScreen(Game game, Assets assets) {
        super(game, assets);
        textGame = new Text(assets, null, 100, 10, "Space\n Game", false);
        textGame.x = width / 2.f;
        textGame.y = height * 4.f / 5.f;

        textTouch = new Text(assets, null, 80, 10, "Touch", true);
        textTouch.x = width / 2.f;
        textTouch.y = height / 2f;

        textOptions = new Text(assets, null, 60, 10, "Options", false);
        textOptions.x = textOptions.getBox().getWidth() / 2;
        textOptions.y = textOptions.getBox().getHeight() / 2;
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(24.f / 255.f, 40.f / 255.f, 60.f / 255.f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.enableBlending();
        batch.begin();
        spriteBackground.setPosition(0, 0);
        spriteBackground.draw(batch);
        spriteBackground.setPosition(0, spriteBackground.getHeight());
        spriteBackground.draw(batch);

        textGame.draw(batch);
        textTouch.draw(batch);
        textOptions.draw(batch);

        batch.end();

        if (touched(textTouch.getBox())) {
            game.setScreen(new SpaceGameScreen(game, assets));
        } else
        if (touched(textOptions.getBox())) {
            game.setScreen(new OptionsScreen(game, assets));
        }

        textTouch.step(delta);
        textGame.step(delta);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

}
