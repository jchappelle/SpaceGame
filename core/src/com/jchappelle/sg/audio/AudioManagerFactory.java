package com.jchappelle.sg.audio;

import com.jchappelle.sg.GameManager;

public final class AudioManagerFactory {

    public static AudioManager make(GameManager gameManager){
        return new DefaultAudioManager(gameManager.getPreferences());
    }

    private AudioManagerFactory(){}
}
