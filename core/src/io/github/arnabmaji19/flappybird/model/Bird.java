package io.github.arnabmaji19.flappybird.model;

import com.badlogic.gdx.graphics.Texture;

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
    private static final float UPWARD_VELOCITY = -5.5f;

    private Texture activeTexture;
    private float xPos;
    private float yPos;
    private int screenHeight;
    private int screenWidth;

    private int birdTextureState = 0;
    private int textureChangeDelay = 0;
    private float velocity = 0;

    public Bird(int screenHeight, int screenWidth) {
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;

        this.xPos = screenWidth / 2.0f - BIRD_WIDTH;
        this.yPos = screenHeight / 2.0f - BIRD_HEIGHT;
        this.activeTexture = TEXTURES[birdTextureState];
    }

    public void move() {

        velocity += GRAVITY;  // increase bird's velocity
        yPos -= velocity;  // change bird's y position

        if (hitsEnd()) yPos = 0;
    }

    public void flyUp() {
        velocity = UPWARD_VELOCITY;
    }

    public void toggleTexture() {
        // toggle active texture in a delay
        if (textureChangeDelay < MAX_TEXTURE_CHANGE_DELAY) textureChangeDelay++;
        else {
            activeTexture = TEXTURES[birdTextureState++];

            if (birdTextureState == TEXTURES.length) birdTextureState = 0;
            textureChangeDelay = 0;
        }

    }

    public Texture getActiveTexture() {
        return activeTexture;
    }

    public float getXPos() {
        return xPos;
    }

    public float getYPos() {
        return yPos;
    }

    public boolean hitsEnd() {
        return yPos <= 0;
    }

}
