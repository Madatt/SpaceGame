package com.mdtt.space.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class GameUtils {
    public static float pingPong(float a, float b, long period)
    {
        long time = TimeUtils.millis() % period;
        if(time < period / 2) {
            return MathUtils.lerp(a, b, time / (period / 2.f));
        }
        return MathUtils.lerp(b, a, (time - period / 2.f) / (period / 2.f));
    }
}
