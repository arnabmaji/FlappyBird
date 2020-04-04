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

    private List<Sound> sounds;
    private Music backgroundMusic;

    public SoundManager() {
        this.sounds = Arrays.asList(
                Gdx.audio.newSound(Gdx.files.internal("audio/die.ogg")),
                Gdx.audio.newSound(Gdx.files.internal("audio/hit.ogg")),
                Gdx.audio.newSound(Gdx.files.internal("audio/point.ogg")),
                Gdx.audio.newSound(Gdx.files.internal("audio/wing.ogg"))

        );

        this.backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/music.mp3"));
        this.backgroundMusic.setVolume(0.5f);

    }

    public void play(int soundId) {
        sounds.get(soundId).play();
    }

    public void playBackgroundMusic() {
        backgroundMusic.setOnCompletionListener(Music::play);
        backgroundMusic.play();
    }

    public void dispose() {
        for (var sound : sounds) sound.dispose();
        backgroundMusic.dispose();
    }
}
