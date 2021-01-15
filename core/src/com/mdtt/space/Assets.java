package com.mdtt.space;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.Dictionary;
import java.util.HashMap;

public class Assets {
    private TextureAtlas atlas;
    private BitmapFont fontBig;
    private BitmapFont fontMedium;
    private HashMap<Integer, BitmapFont> fonts = new HashMap<>();

    public void load() {
        atlas = new TextureAtlas(Gdx.files.internal("atlas.txt"));

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.borderStraight = true;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 10.5f;

        parameter.size = 70;
        fontBig = generator.generateFont(parameter);

        parameter.size = 54;
        fontMedium = generator.generateFont(parameter);
    }

        public Texture getTexture(String name) {
            return atlas.findRegion(name).getTexture();
        }

        public Sprite getSprite(String name) {
            return atlas.createSprite(name);
        }

        public Sprite getSprite(String name, int index) {
            return atlas.createSprite(name, index);
        }

        public BitmapFont getFont(int size, int stroke) {
            if(!fonts.containsKey(size)) {
                FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
                FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
                parameter.borderStraight = true;
                parameter.borderColor = Color.BLACK;
                parameter.borderWidth = stroke;
                parameter.size = size - stroke;
                fonts.put(size, generator.generateFont(parameter));
            }

            return fonts.get(size);
        }

        public BitmapFont getFontBig() {
            return fontBig;
        }

        public BitmapFont getFontMedium() {
            return fontMedium;
        }

        public TextureAtlas getAtlas() {
        return atlas;
    }
}