package com.jchappelle.sg;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.audio.Sound;
import com.jchappelle.sg.entities.EntityFactory;
import com.jchappelle.sg.preferences.AppPreferences;
import com.jchappelle.sg.screens.ScreenId;

public interface GameManager {

    void setPlayer(Entity entity);

    Entity getPlayer();

    void changeScreen(ScreenId screen);

    AppPreferences getPreferences();

    Sound getSound(SoundId soundId);

    void playSound(SoundId soundId);

    EntityFactory getEntityFactory();

}
