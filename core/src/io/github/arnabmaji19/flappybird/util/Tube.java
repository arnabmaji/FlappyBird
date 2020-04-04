package io.github.arnabmaji19.flappybird.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import io.github.arnabmaji19.flappybird.model.Screen;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;


public class Tube {

    private static final Texture UPPER_TUBE_TEXTURE = new Texture("sprites/top-tube.png");  // texture for upper tube
    private static final Texture LOWER_TUBE_TEXTURE = new Texture("sprites/bottom-tube.png");  // texture for lower tube
    private static final int TUBE_LENGTH = UPPER_TUBE_TEXTURE.getHeight();
    private static final int GAP_HEIGHT = 150;  // height of gap between upper and lower tubes
    private static final int LOWER_RANGE = (Screen.HEIGHT) / 14;  // lower range for y-pos of gap between upper and lower tube
    private static final int UPPER_RANGE = (Screen.HEIGHT * 11) / 14 - GAP_HEIGHT;  // upper range for y-pos of gap between upper and lower tube
    private static final int TUBE_BEGIN_X_POSITION = Screen.WIDTH;  // starting x position for all new tubes
    private static final int MAX_TUBE_CREATION_DELAY = 120;  // delay for creating tubes on screen
    private static final float TUBE_VELOCITY = -3.0f;  // Tube's velocity along negative x-axis

    private Queue<TubePosition> activeTubes;  // currently active tubes
    private Queue<TubePosition> inActiveTubes;  // tubes out of screen
    private JustCrossedListener justCrossedListener;
    private int tubeCreationDelay = 0;

    public Tube() {
        this.activeTubes = new LinkedList<>();
        this.inActiveTubes = new LinkedList<>();
    }

    public void move() {

        for (var tubePos : activeTubes) {
            tubePos.xPos += TUBE_VELOCITY;  // move all active tubes along negative x-axis
            // check for inactive tubes
            if (tubePos.xPos <= -UPPER_TUBE_TEXTURE.getWidth()) inActiveTubes.add(tubePos);
        }
        // remove all inactive tubes
        activeTubes.removeAll(inActiveTubes);
    }

    public void createInDelay() {

        if (tubeCreationDelay < MAX_TUBE_CREATION_DELAY) tubeCreationDelay++;
        else {
            create();
            tubeCreationDelay = 0;
        }
    }

    public void draw(SpriteBatch batch) {
        // draw all active tubes on screen
        for (var tubePos : activeTubes) {
            batch.draw(UPPER_TUBE_TEXTURE, tubePos.xPos, tubePos.upperTubeY);
            batch.draw(LOWER_TUBE_TEXTURE, tubePos.xPos, tubePos.lowerTubeY);
        }
    }

    private void create() {
        // generate random Y-coordinate for gap
        float gapY = (float) (Math.random() * (float) UPPER_RANGE) + LOWER_RANGE;
        float lowerTubeY = gapY - TUBE_LENGTH;  // determine Y-coordinate of lower tube
        float upperTubeY = GAP_HEIGHT + gapY;  // determine Y-coordinate of upper tube
        activeTubes.add(new TubePosition(TUBE_BEGIN_X_POSITION, upperTubeY, lowerTubeY));

    }

    public boolean hitsBird(Bird bird) {

        for (var tubePos : activeTubes) {

            float birdRear = bird.getXPos();
            float birdFront = birdRear + Bird.getWidth();
            float tubeFront = tubePos.xPos;
            float tubeRear = tubeFront + UPPER_TUBE_TEXTURE.getWidth();

            if (birdFront >= tubeFront && birdRear <= tubeRear) {  // only check for tubes that are nearest
                // create rectangle for bird's current position
                var birdRectangle = new Rectangle(
                        birdRear,
                        bird.getYPos(),
                        Bird.getWidth(),
                        Bird.getHeight()
                );

                // create rectangle for upper tube's current position
                var upperTubeRectangle = new Rectangle(
                        tubeFront,
                        tubePos.upperTubeY,
                        UPPER_TUBE_TEXTURE.getWidth(),
                        UPPER_TUBE_TEXTURE.getHeight()
                );

                // create rectangle for lower tube's current position
                var lowerTubeRectangle = new Rectangle(
                        tubeFront,
                        tubePos.lowerTubeY,
                        LOWER_TUBE_TEXTURE.getWidth(),
                        LOWER_TUBE_TEXTURE.getHeight()
                );

                // check if bird hits any of the tubes
                if (
                        Intersector.overlaps(birdRectangle, upperTubeRectangle) ||
                                Intersector.overlaps(birdRectangle, lowerTubeRectangle)
                ) return true;

            }

            // check if bird just crossed any tube
            var distance = birdRear - tubeRear;
            if (distance >= 0.0f && distance < -TUBE_VELOCITY) justCrossedListener.justCrossed();

        }
        return false;
    }

    public void addJustCrossedListener(JustCrossedListener justCrossedListener) {
        this.justCrossedListener = justCrossedListener;
    }


    // inner class for tracking upper and lower tube positions
    private static class TubePosition {

        private float xPos;
        private float upperTubeY;
        private float lowerTubeY;

        public TubePosition(float xPos, float upperTubeY, float lowerTubeY) {
            this.xPos = xPos;
            this.upperTubeY = upperTubeY;
            this.lowerTubeY = lowerTubeY;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TubePosition that = (TubePosition) o;
            return Float.compare(that.xPos, xPos) == 0 &&
                    Float.compare(that.upperTubeY, upperTubeY) == 0 &&
                    Float.compare(that.lowerTubeY, lowerTubeY) == 0;
        }

        @Override
        public int hashCode() {
            return Objects.hash(xPos, upperTubeY, lowerTubeY);
        }
    }

    public static void dispose() {
        UPPER_TUBE_TEXTURE.dispose();
        LOWER_TUBE_TEXTURE.dispose();
    }

    public interface JustCrossedListener {
        void justCrossed();
    }

}
