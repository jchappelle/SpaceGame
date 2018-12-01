package com.jchappelle.sg.persistence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.jchappelle.sg.GameManager;
import com.jchappelle.sg.preferences.AppPreferences;

public class GameDB {

    private FileHandle handle;
    private GameManager gameManager;
    private static final String SAVE_FILE = "gamedev/spacegame/data.json";

    public GameDB(GameManager gameManager){
        this.gameManager = gameManager;
    }

    public void load(){
        handle = Gdx.files.external(SAVE_FILE);
        if(!handle.exists()){
            save();
        }
        else{
            Json json = new Json();
            PreferencesState state = json.fromJson(PreferencesState.class, handle);
            AppPreferences prefs = gameManager.getPreferences();
            prefs.setMusicVolume(state.musicVolume);
            prefs.setSoundEffectsEnabled(state.soundEnabled);
            prefs.setSoundVolume(state.soundVolume);
            prefs.setMusicEnabled(state.musicEnabled);
        }
    }

    public void save(){
        handle = Gdx.files.external(SAVE_FILE);
        Json json = new Json();

        AppPreferences prefs = gameManager.getPreferences();

        PreferencesState state = new PreferencesState();
        state.musicVolume = prefs.getMusicVolume();
        state.musicEnabled = prefs.isMusicEnabled();
        state.soundEnabled = prefs.isSoundEffectsEnabled();
        state.soundVolume = prefs.getSoundVolume();

        boolean prettyPrint = true;
        String serializedGameState = prettyPrint ? json.prettyPrint(state) : json.toJson(state);
        handle.writeString(serializedGameState, false);

    }

}
