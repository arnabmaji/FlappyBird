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
    private Texture gameOverTexture;

    private Bird bird;
    private Tube tube;
    private GameState gameState;

    @Override
    public void create() {
        batch = new SpriteBatch();
        backgroundImage = new Texture("background-day.png");
        gameOverTexture = new Texture("game-over.png");

        startNewGame();
    }

    @Override
    public void render() {

        if (Gdx.input.justTouched()) {  // on touch
            if (gameState.equals(GameState.RUNNING)) bird.flyUp();  // fly in upward direction
            else startNewGame();
        }


        batch.begin();
        // draw background image
        batch.draw(backgroundImage, 0, 0, Screen.WIDTH, Screen.HEIGHT);

        // if game is over
        if (gameState.equals(GameState.GAME_OVER)) {
            // draw game over screen
            batch.draw(gameOverTexture,
                    Screen.WIDTH / 2.0f - gameOverTexture.getWidth() / 2.0f,
                    Screen.HEIGHT / 2.0f - gameOverTexture.getHeight() / 2.0f);
        } else {
            // bird configurations
            bird.fly();
            bird.draw(batch);  // draw bird on the screen

            // tube configurations
            tube.createInDelay();  // create new tubes in a delay
            tube.move();  // move all active tubes along negative x-axis
            tube.draw(batch);  // draw active tubes on the screen
            if (tube.hitsBird(bird) || bird.hitsEnd()) {
                // game over
                gameState = GameState.GAME_OVER;
            }
        }

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        backgroundImage.dispose();
        gameOverTexture.dispose();
    }

    private void startNewGame() {
        bird = new Bird();
        tube = new Tube();
        gameState = GameState.RUNNING;
        tube.addJustCrossedListener(() -> System.out.println("Just crossed!"));
    }

    private enum GameState {RUNNING, GAME_OVER}
}
