package io.github.arnabmaji19.flappybird.util;

import com.badlogic.gdx.graphics.Texture;

import java.util.Arrays;
import java.util.List;

public class GameBackgroundImage {

    private static final List<Texture> textures = Arrays.asList(
            new Texture("sprites/background-day.png"),
            new Texture("sprites/background-night.png"));

    public static final int DAY = 0;
    public static final int NIGHT = 1;

    private int activeBackground = 0;

    public Texture get() {
        return textures.get(activeBackground);
    }

    public void set(int background) {
        activeBackground = background;
    }

    public static void dispose() {
        for (var t : textures) t.dispose();
    }

}
