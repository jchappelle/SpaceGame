package com.jchappelle.sg;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.jchappelle.sg.audio.AudioManager;
import com.jchappelle.sg.entities.EntityFactory;
import com.jchappelle.sg.preferences.AppPreferences;
import com.jchappelle.sg.screens.ScreenId;
import com.jchappelle.sg.systems.render.ScrollingBackground;

public interface GameManager extends PlayerProvider {

    void setPlayer(Entity entity);

    boolean changeToPreviousScreen();

    void changeScreen(ScreenId screen);

    AppPreferences getPreferences();

    EntityFactory getEntityFactory();

    AudioManager getAudioManager();

    ScrollingBackground getBackground();

    BitmapFont newFont(int fontSize);
}
