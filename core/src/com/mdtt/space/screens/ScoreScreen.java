package com.mdtt.space.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.mdtt.space.Assets;
import com.mdtt.space.entities.decor.Text;

public class ScoreScreen extends CommonScreen implements Screen {
    private Text textScore;
    private Text textTouch;

    public ScoreScreen(Game game, Assets assets, int score) {
        super(game, assets);
        textScore = new Text(assets, null, 100, 10,
                "Score\n" + String.format("%07d", score), false);
        textScore.x = width / 2.f;
        textScore.y = height * 4.f / 5.f;

        textTouch = new Text(assets, null, 80, 10, "Touch", true);
        textTouch.x = width / 2.f;
        textTouch.y = height / 2f;
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

        textScore.draw(batch);
        textTouch.draw(batch);

        batch.end();


        if (touched()) {
            game.setScreen(new MenuScreen(game, assets));
        }

        textTouch.step(delta);
        textScore.step(delta);
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
