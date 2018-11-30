package com.jchappelle.sg;

import com.badlogic.gdx.audio.Sound;
import com.jchappelle.sg.preferences.AppPreferences;
import com.jchappelle.sg.screens.ScreenId;

public interface GameManager {

    void changeScreen(ScreenId screen);

    AppPreferences getPreferences();

    Sound getSound(SoundId soundId);

    void playSound(SoundId soundId);

}
