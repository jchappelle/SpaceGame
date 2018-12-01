package com.jchappelle.sg.audio;

import com.badlogic.gdx.utils.Disposable;

public interface AudioManager extends Disposable {

    void playSound(SoundId soundId);

    void playMusic(MusicId musicId);

}
