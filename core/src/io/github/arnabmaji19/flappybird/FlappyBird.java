package io.github.arnabmaji19.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.github.arnabmaji19.flappybird.model.Bird;

public class FlappyBird extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture backgroundImage;

    private Bird bird;

    private int screenHeight;
    private int screenWidth;

    @Override
    public void create() {
        batch = new SpriteBatch();
        backgroundImage = new Texture("background-day.png");

        screenHeight = Gdx.graphics.getHeight();
        screenWidth = Gdx.graphics.getWidth();
        bird = new Bird(screenHeight, screenWidth);
    }

    @Override
    public void render() {

        if (Gdx.input.justTouched()) {
            bird.flyUp();
        }

        batch.begin();
        // draw background image
        batch.draw(backgroundImage, 0, 0, screenWidth, screenHeight);

        // bird configurations
        bird.toggleTexture();
        bird.move();

        TextureRegion region = new TextureRegion(bird.getActiveTexture(), Bird.getWidth(), Bird.getHeight());
        batch.draw(region, bird.getXPos(), bird.getYPos(), 0.0f, 0.0f,
                Bird.getWidth(), Bird.getHeight(),
                1.0f, 1.0f, bird.getFlyingAngle());
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        backgroundImage.dispose();
    }
}
