package com.mdtt.space.entities.decor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.mdtt.space.Assets;
import com.mdtt.space.entities.common.Entity;
import com.mdtt.space.screens.SpaceGameScreen;

public class SpriteDecor extends Entity {

    private Sprite sprite = null;

    public SpriteDecor(Assets assets, SpaceGameScreen screen) {
        super(assets, screen);
    }

    @Override
    public Rectangle getBox() {
        if(sprite == null) {
            return new Rectangle();
        }
        return new Rectangle(x - sprite.getOriginX(), y - sprite.getOriginY(),
                sprite.getWidth(), sprite.getHeight());
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    @Override
    public void entityStep(float delta) {

    }

    @Override
    public void draw(Batch batch) {
        if(sprite != null) {
            sprite.setOriginCenter();
            sprite.setPosition(x - sprite.getOriginX(), y - sprite.getOriginY());
            sprite.draw(batch);
        }
    }

    @Override
    public void die() {

    }
}
