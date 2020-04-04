package io.github.arnabmaji19.flappybird.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.Arrays;
import java.util.List;

public class SoundManager {

    public static final int DIE = 0;
    public static final int HIT = 1;
    public static final int POINT = 2;
    public static final int WING = 3;
    public static final int GAME_OVER = 4;

    public static final int PLAY = 1;
    public static final int STOP = 2;

    private List<Sound> sounds;
    private Music backgroundMusic;

    public SoundManager() {
        this.sounds = Arrays.asList(
                Gdx.audio.newSound(Gdx.files.internal("audio/die.ogg")),
                Gdx.audio.newSound(Gdx.files.internal("audio/hit.ogg")),
                Gdx.audio.newSound(Gdx.files.internal("audio/point.ogg")),
                Gdx.audio.newSound(Gdx.files.internal("audio/wing.ogg")),
                Gdx.audio.newSound(Gdx.files.internal("audio/gameover.wav"))

        );

        this.backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/music.mp3"));
        this.backgroundMusic.setVolume(0.5f);
        backgroundMusic.setOnCompletionListener(Music::play);

    }

    public void play(int soundId) {
        sounds.get(soundId).play();
    }

    public void backgroundMusic(int action) {
        switch (action) {
            case PLAY:
                if (!backgroundMusic.isPlaying()) backgroundMusic.play();
                break;

            case STOP:
                if (backgroundMusic.isPlaying()) backgroundMusic.stop();
                break;
        }
    }

    public void dispose() {
        for (var sound : sounds) sound.dispose();
        backgroundMusic.dispose();
    }
}
