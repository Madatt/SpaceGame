package com.mdtt.space.entities.decor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.mdtt.space.Assets;
import com.mdtt.space.entities.common.Entity;
import com.mdtt.space.game.GameUtils;
import com.mdtt.space.screens.SpaceGameScreen;

public class Text extends Entity {
    private GlyphLayout layout;
    private BitmapFont font;
    private String text;
    private boolean blink;
    private float blinkChange = 2f;
    private float transparency = 1.f;


    public Text(Assets assets, SpaceGameScreen screen, int size, int stroke, String text, boolean blink) {
        super(assets, screen);
        font = assets.getFont(size, stroke);
        this.text = text;
        layout = new GlyphLayout(font, text);
        this.blink = blink;
    }

    public void setTransparency(float t) {
        transparency = t;
    }

    public void setBlink(boolean blink) {
        this.blink = blink;
    }

    public void setText(String text) {
        this.text = text;
        layout = new GlyphLayout(font, text);
    }


    public void setText(int text) {
        setText(Integer.toString(text));
    }

    @Override
    public Rectangle getBox() {
        return new Rectangle(x - layout.width / 2, y - layout.height / 2, layout.width, layout.height);
    }

    @Override
    public void entityStep(float delta) {
        if(blink) {
            transparency = GameUtils.pingPong(0.f, 1.f, 1200);
        }
    }

    @Override
    public void draw(Batch batch) {
        font.setColor(1, 1,1, transparency);
        font.draw(batch, text, x - layout.width / 2, y - layout.height / 2);
    }

    @Override
    public void die() {

    }
}
