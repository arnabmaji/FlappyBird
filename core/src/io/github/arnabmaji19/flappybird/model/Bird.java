package io.github.arnabmaji19.flappybird.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Bird {

    // all possible textures for bird
    private static final Texture[] TEXTURES = {
            new Texture("bird-downflap.png"),
            new Texture("bird-midflap.png"),
            new Texture("bird-upflap.png")
    };
    private static final int BIRD_WIDTH = TEXTURES[0].getWidth();
    private static final int BIRD_HEIGHT = TEXTURES[0].getHeight();
    private static final int MAX_TEXTURE_CHANGE_DELAY = 3;
    private static final float GRAVITY = 0.2f;
    private static final float UPWARD_VELOCITY = -4.5f;

    private Texture activeTexture;
    private TextureRegion textureRegion;
    private float xPos;
    private float yPos;

    private int birdTextureState = 0;
    private int textureChangeDelay = 0;
    private float velocity;
    private float flyingAngle;

    public Bird() {
        this.xPos = Screen.WIDTH / 2.5f;
        this.activeTexture = TEXTURES[birdTextureState];
        this.textureRegion = new TextureRegion(activeTexture, BIRD_WIDTH, BIRD_HEIGHT);
        this.yPos = Screen.HEIGHT / 2.0f;
        this.velocity = 0.0f;
        this.flyingAngle = 0.0f;
    }

    public void fly() {

        velocity += GRAVITY;  // increase bird's velocity
        yPos -= velocity;  // change bird's y position
        flyingAngle -= 1.0f;  // change flying angle

        if (flyingAngle <= -90.0f) flyingAngle = -90.0f;
        toggleTexture();  // animate bird's fly

    }

    public void flyUp() {
        velocity = UPWARD_VELOCITY;
        flyingAngle = 20.0f;
    }

    public void draw(SpriteBatch batch) {
        textureRegion.setTexture(activeTexture);
        // draw bird in the batch
        batch.draw(textureRegion, xPos, yPos, 0.0f, 0.0f,
                BIRD_WIDTH, BIRD_HEIGHT,
                1.0f, 1.0f, flyingAngle);
    }

    public boolean hitsEnd() {
        return yPos <= BIRD_HEIGHT || yPos + BIRD_HEIGHT >= Screen.HEIGHT;
    }


    private void toggleTexture() {
        // toggle active texture in a delay
        if (textureChangeDelay < MAX_TEXTURE_CHANGE_DELAY) textureChangeDelay++;
        else {
            activeTexture = TEXTURES[birdTextureState++];

            if (birdTextureState == TEXTURES.length) birdTextureState = 0;
            textureChangeDelay = 0;
        }
    }

    public float getXPos() {
        return xPos;
    }

    public float getYPos() {
        return yPos;
    }

    public static int getHeight() {
        return BIRD_HEIGHT;
    }

    public static int getWidth() {
        return BIRD_WIDTH;
    }
}
