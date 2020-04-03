package io.github.arnabmaji19.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.arnabmaji19.flappybird.model.Bird;
import io.github.arnabmaji19.flappybird.model.Screen;
import io.github.arnabmaji19.flappybird.model.Tube;

public class FlappyBird extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture backgroundImage;

    private Bird bird;
    private Tube tube;


    @Override
    public void create() {
        batch = new SpriteBatch();
        backgroundImage = new Texture("background-day.png");

        bird = new Bird();
        tube = new Tube();
    }

    @Override
    public void render() {

        if (Gdx.input.justTouched()) {  // on touch
            bird.flyUp();  // fly in upward direction
        }

        batch.begin();
        // draw background image
        batch.draw(backgroundImage, 0, 0, Screen.WIDTH, Screen.HEIGHT);

        // bird configurations
        bird.fly();
        bird.draw(batch);  // draw bird on the screen

        // tube configurations
        tube.createInDelay();  // create new tubes in a delay
        tube.move();  // move all active tubes along negative x-axis
        tube.draw(batch);  // draw active tubes on the screen

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        backgroundImage.dispose();
    }
}
