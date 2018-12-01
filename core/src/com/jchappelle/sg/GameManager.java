package com.jchappelle.sg;

import com.badlogic.ashley.core.Entity;
import com.jchappelle.sg.audio.AudioManager;
import com.jchappelle.sg.entities.EntityFactory;
import com.jchappelle.sg.preferences.AppPreferences;
import com.jchappelle.sg.screens.ScreenId;

public interface GameManager extends PlayerProvider {

    void setPlayer(Entity entity);

    boolean changeToPreviousScreen();

    void changeScreen(ScreenId screen);

    AppPreferences getPreferences();

    EntityFactory getEntityFactory();

    AudioManager getAudioManager();

}
