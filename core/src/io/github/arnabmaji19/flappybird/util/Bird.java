package io.github.arnabmaji19.flappybird.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.github.arnabmaji19.flappybird.model.Screen;

public class Bird {

    public static final int YELLOW = 0;
    public static final int BLUE = 1;
    public static final int RED = 2;

    // all possible textures for bird
    private static final Texture[][] TEXTURES = {
            {
                    new Texture("sprites/bird/yellowbird-downflap.png"),
                    new Texture("sprites/bird/yellowbird-midflap.png"),
                    new Texture("sprites/bird/yellowbird-upflap.png")
            },

            {
                    new Texture("sprites/bird/bluebird-downflap.png"),
                    new Texture("sprites/bird/bluebird-midflap.png"),
                    new Texture("sprites/bird/bluebird-upflap.png")
            },
            {
                    new Texture("sprites/bird/redbird-downflap.png"),
                    new Texture("sprites/bird/redbird-midflap.png"),
                    new Texture("sprites/bird/redbird-upflap.png")
            }
    };

    private static final int BIRD_WIDTH = TEXTURES[0][0].getWidth();
    private static final int BIRD_HEIGHT = TEXTURES[0][0].getHeight();
    private static final int MAX_TEXTURE_CHANGE_DELAY = 3;
    private static final float GRAVITY = 0.2f;
    private static final float UPWARD_VELOCITY = -5.0f;

    private Texture activeTexture;
    private int activeColor = 0;
    private TextureRegion textureRegion;
    private float xPos;
    private float yPos;

    private int birdTextureState = 0;
    private int textureChangeDelay = 0;
    private float velocity;
    private float flyingAngle;

    public Bird() {
        this.xPos = Screen.WIDTH / 2.5f;
        this.activeTexture = TEXTURES[activeColor][birdTextureState];
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
            activeTexture = TEXTURES[activeColor][birdTextureState++];

            if (birdTextureState == TEXTURES.length) birdTextureState = 0;
            textureChangeDelay = 0;
        }
    }

    public void setColor(int color) {
        this.activeColor = color;
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

    public static void dispose() {
        for (var texture : TEXTURES) for (var t : texture) t.dispose();
    }
}
