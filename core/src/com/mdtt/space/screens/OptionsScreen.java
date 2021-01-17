package com.mdtt.space.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mdtt.space.Assets;
import com.mdtt.space.SpaceGame;
import com.mdtt.space.entities.decor.SpriteDecor;
import com.mdtt.space.entities.decor.Text;
import com.mdtt.space.game.systems.GameSystem;

public class OptionsScreen extends CommonScreen implements Screen {
    private Text textGame;
    private Text textBack;
    private Text textSkin;
    private Text textHealth;
    private Text textHealthButton;
    private SpriteDecor spriteSkin;

    public OptionsScreen(Game game, Assets assets) {
        super(game, assets);
        textGame = new Text(assets, null, 100, 10, "Options", false);
        textGame.x = width / 2.f;
        textGame.y = height * 4.f / 5.f;

        textBack = new Text(assets, null, 60, 10, "Back", false);
        textBack.x = textBack.getBox().getWidth() / 2;
        textBack.y = textBack.getBox().getHeight() / 2;

        spriteSkin = new SpriteDecor(assets, null);
        spriteSkin.x = width / 2.f;
        spriteSkin.y = height / 2.f;

        textSkin = new Text(assets, null, 40, 5, "Tap to change", false);
        textSkin.x = width/2.f;

        textHealth = new Text(assets, null, 50, 5, "Health: 100", false);
        textHealth.x = width/2.f;
        textHealth.setText("Health: " + Integer.toString(SpaceGame.playerHealth));
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

        spriteSkin.setSprite(SpaceGame.getPlayerSkin());

        textSkin.y =  height * 4.f / 5.f - textGame.getBox().getHeight();
        textHealth.y = spriteSkin.y - spriteSkin.getBox().getHeight() * 2;
        textHealth.setText("Health: " + Integer.toString(SpaceGame.playerHealth));

        textGame.draw(batch);
        textBack.draw(batch);
        textSkin.draw(batch);
        textHealth.draw(batch);
        spriteSkin.draw(batch);

        batch.end();

        if (touched(textBack.getBox())) {
            game.setScreen(new MenuScreen(game, assets));
        } else if (touched(spriteSkin.getBox())) {
            Gdx.app.log("OK", "OK");
            SpaceGame.changePlayerSkin();
        } else if (touched(textHealth.getBox())) {
            SpaceGame.changePlayerHealth();
        }

        textBack.step(delta);
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
