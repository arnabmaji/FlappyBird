package io.github.arnabmaji19.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.arnabmaji19.flappybird.model.Screen;
import io.github.arnabmaji19.flappybird.util.*;

public class FlappyBird extends ApplicationAdapter {

    private SpriteBatch batch;
    private Texture gameOverTexture;

    private Bird bird;
    private Tube tube;
    private ScoreBoard scoreBoard;
    private GameState gameState;
    private GameMessageTexture gameMessageTexture;
    private SoundManager soundManager;
    private GameBackgroundImage gameBackgroundImage;

    @Override
    public void create() {
        batch = new SpriteBatch();
        gameOverTexture = new Texture("sprites/game-over.png");

        gameMessageTexture = new GameMessageTexture(new Texture("sprites/message.png"));

        soundManager = new SoundManager();
        soundManager.backgroundMusic(SoundManager.PLAY);

        gameBackgroundImage = new GameBackgroundImage();

        scoreBoard = new ScoreBoard();
        scoreBoard.addScoreListener(score -> {
            if (score == 25) {
                // change background to night
                gameBackgroundImage.set(GameBackgroundImage.NIGHT);
            } else if (score == 50) {
                bird.setColor(Bird.BLUE);
                gameBackgroundImage.set(GameBackgroundImage.DAY);
                tube.decreaseCreationDelay(40);

            } else if (score == 70) {
                bird.setColor(Bird.RED);
                gameBackgroundImage.set(GameBackgroundImage.NIGHT);
                bird.increaseJumpVelocity();
            } else if (score == 99) {
                // create last tube
                tube.createLastTube();
            }
        });

        gameState = GameState.READY;
    }

    @Override
    public void render() {

        if (Gdx.input.justTouched()) {  // on touch
            if (gameState.equals(GameState.RUNNING)) {
                bird.flyUp();  // fly in upward direction
                soundManager.play(SoundManager.WING);  // play wing sound
            } else if (gameState.equals(GameState.READY)) startNewGame();
                // game state: game over
            else {
                soundManager.backgroundMusic(SoundManager.PLAY);
                gameState = GameState.READY;
            }
        }


        batch.begin();
        // draw background image);
        batch.draw(gameBackgroundImage.get(), 0, 0, Screen.WIDTH, Screen.HEIGHT);

        // if game is over
        if (gameState.equals(GameState.GAME_OVER)) {

            bird.draw(batch);  // draw bird on the screen
            tube.draw(batch);  // draw active tubes on the screen

            // draw game over screen
            batch.draw(gameOverTexture,
                    Screen.WIDTH / 2.0f - gameOverTexture.getWidth() / 2.0f,
                    Screen.HEIGHT / 2.0f - gameOverTexture.getHeight() / 2.0f);
            scoreBoard.draw(batch);
        } else if (gameState.equals(GameState.RUNNING)) {

            // bird configurations
            bird.fly();
            bird.draw(batch);  // draw bird on the screen

            // tube configurations
            tube.createInDelay();  // create new tubes in a delay
            tube.move();  // move all active tubes along negative x-axis
            tube.draw(batch);  // draw active tubes on the screen

            // game over
            if (tube.hitsBird(bird) || bird.hitsEnd()) {
                soundManager.play(bird.hitsEnd() ? SoundManager.DIE : SoundManager.HIT);
                // change game state
                gameState = GameState.GAME_OVER;
                soundManager.backgroundMusic(SoundManager.STOP);  // pause background music
                soundManager.play(SoundManager.GAME_OVER); // play game over sound effect
            }

            scoreBoard.draw(batch);

        } else {
            gameMessageTexture.draw(batch);
        }

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        gameOverTexture.dispose();
        soundManager.dispose();
        Bird.dispose();
        Tube.dispose();
        ScoreBoard.dispose();
        GameBackgroundImage.dispose();
    }

    private void startNewGame() {
        // create new game
        bird = new Bird();
        tube = new Tube();
        gameState = GameState.RUNNING;
        scoreBoard.reset();
        gameBackgroundImage.set(GameBackgroundImage.DAY);
        tube.addJustCrossedListener(() -> {
            scoreBoard.increment();  // increment score
            scoreBoard.createScoreList();  // update numbers for displaying score
            soundManager.play(SoundManager.POINT);  // play sound
        });

        scoreBoard.createScoreList();  // create numbers for displaying score
    }

    private enum GameState {READY, RUNNING, GAME_OVER}
}
