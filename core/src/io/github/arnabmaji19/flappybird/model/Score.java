package io.github.arnabmaji19.flappybird.model;

import com.badlogic.gdx.graphics.Texture;

public class Score {

    private Texture score;
    private int x;
    private int y;

    public Score(Texture score, int x, int y) {
        this.score = score;
        this.x = x;
        this.y = y;
    }

    public Texture getScore() {
        return score;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
