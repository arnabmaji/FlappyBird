package io.github.arnabmaji19.flappybird.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public class ScoreBoard {

    private static final int STEP = 1;
    private static final Texture[] numbers = {
            new Texture("sprites/0.png"),
            new Texture("sprites/1.png"),
            new Texture("sprites/2.png"),
            new Texture("sprites/3.png"),
            new Texture("sprites/4.png"),
            new Texture("sprites/5.png"),
            new Texture("sprites/6.png"),
            new Texture("sprites/7.png"),
            new Texture("sprites/8.png"),
            new Texture("sprites/9.png"),
    };

    private List<Score> scoreList;
    private int score = 0;

    public ScoreBoard() {
        this.scoreList = new ArrayList<>();
    }

    public void increment() {
        score += STEP;
    }

    public void reset() {
        score = 0;
    }

    public void draw(SpriteBatch batch) {
        for (var score : scoreList) {
            batch.draw(score.getScore(), score.getX(), score.getY());
        }
    }

    public void createScoreList() {
        scoreList.clear();
        int x = 10;
        int y = 10;

        for (var digit : (score + "").toCharArray()) {
            var texture = numbers[Integer.valueOf(digit + "")];
            scoreList.add(new Score(texture, x, y));
            x += texture.getWidth();
        }
    }

}
