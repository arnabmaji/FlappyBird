package io.github.arnabmaji19.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FlappyBird extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture backgroundImage;

    private int screenHeight;
    private int screenWidth;

    @Override
    public void create() {
        batch = new SpriteBatch();
        backgroundImage = new Texture("background-day.png");

        screenHeight = Gdx.graphics.getHeight();
        screenWidth = Gdx.graphics.getWidth();
    }

    @Override
    public void render() {
        batch.begin();
        // draw background image
        batch.draw(backgroundImage, 0, 0, screenWidth, screenHeight);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        backgroundImage.dispose();
    }
}
