package com.jchappelle.sg.preferences;

public interface AppPreferences {
    boolean isSoundEffectsEnabled();

    void setSoundEffectsEnabled(boolean soundEffectsEnabled);

    boolean isMusicEnabled();

    void setMusicEnabled(boolean musicEnabled);

    float getMusicVolume();

    void setMusicVolume(float volume);

    float getSoundVolume();

    void setSoundVolume(float volume);
}