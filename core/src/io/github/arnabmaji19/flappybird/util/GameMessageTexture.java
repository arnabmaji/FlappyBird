package io.github.arnabmaji19.flappybird.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.arnabmaji19.flappybird.model.Screen;

public class GameMessageTexture {

    private Texture texture;
    private float x;
    private float y;

    public GameMessageTexture(Texture texture) {
        this.texture = texture;
        this.x = Screen.WIDTH / 2.0f - texture.getWidth() / 2.0f;
        this.y = Screen.HEIGHT / 2.0f - texture.getHeight() / 2.0f;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }
}
