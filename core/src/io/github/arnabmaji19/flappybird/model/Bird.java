package io.github.arnabmaji19.flappybird.model;

import com.badlogic.gdx.graphics.Texture;

public class Bird {

    // all possible textures for bird
    private static final Texture[] textures = {
            new Texture("bird-downflap.png"),
            new Texture("bird-midflap.png"),
            new Texture("bird-upflap.png")
    };

    private static final int birdWidth = textures[0].getWidth();
    private static final int birdHeight = textures[0].getHeight();
    private static final int MAX_TEXTURE_CHANGE_DELAY = 3;

    private Texture activeTexture;
    private int xPos;
    private int yPos;
    private int screenHeight;
    private int screenWidth;

    private int birdTextureState = 0;
    private int textureChangeDelay = 0;

    public Bird(int screenHeight, int screenWidth) {
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;

        this.xPos = screenWidth / 2 - birdWidth;
        this.yPos = screenHeight / 2 - birdHeight;
        this.activeTexture = textures[birdTextureState];
    }

    public void toggleTexture() {
        // toggle active texture in a delay
        if (textureChangeDelay < MAX_TEXTURE_CHANGE_DELAY) textureChangeDelay++;
        else {
            activeTexture = textures[birdTextureState++];

            if (birdTextureState == textures.length) birdTextureState = 0;
            textureChangeDelay = 0;
        }

    }

    public Texture getActiveTexture() {
        return activeTexture;
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }
}
