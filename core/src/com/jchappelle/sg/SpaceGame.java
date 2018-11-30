package com.jchappelle.sg;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Sound;
import com.jchappelle.sg.crashhandler.CrashHandler;
import com.jchappelle.sg.crashhandler.CrashHandlerFactory;
import com.jchappelle.sg.preferences.AppPreferences;
import com.jchappelle.sg.preferences.AppPreferencesFactory;
import com.jchappelle.sg.screens.*;

import java.util.HashMap;
import java.util.Map;

class SpaceGame extends Game implements GameManager {

	private static final ScreenId INITIAL_SCREEN = ScreenId.MAIN_MENU;

	private Map<ScreenId, Screen> screens = new HashMap<ScreenId, Screen>();
	private AppPreferences prefs;
	private CrashHandler crashHandler;
	private Map<SoundId, Sound> sounds = new HashMap<SoundId, Sound>();

	public void changeScreen(ScreenId screenId){
		Screen screen = screens.get(screenId);
		if(screen == null){
			screen = ScreenFactory.makeScreen(screenId, this);
			screens.put(screenId, screen);
		}
		setScreen(screen);
	}

	public AppPreferences getPreferences(){
		return prefs;
	}

	@Override
	public Sound getSound(SoundId soundId) {
		return sounds.get(soundId);
	}

	@Override
	public void playSound(SoundId soundId) {
		Sound sound = getSound(soundId);
		if(sound != null){
			sound.play();
		}
	}

	@Override
	public void create () {
		prefs = AppPreferencesFactory.make();
		crashHandler = CrashHandlerFactory.make(this);
		loadSounds();
		changeScreen(INITIAL_SCREEN);
	}

	@Override
	public void render () {
		try{
			if (screen != null) screen.render(Gdx.graphics.getDeltaTime());
		}
		catch(RuntimeException e){
			crashHandler.handleCrash(e);
		}
	}

	private void loadSounds(){
		for(SoundId soundId : SoundId.values()){
			loadSound(soundId);
		}
	}

	private void loadSound(SoundId soundId){
		sounds.put(soundId, Gdx.audio.newSound(Gdx.files.internal(soundId.getPath())));
	}

	@Override
	public void dispose () {
		if (screen != null) {
			screen.dispose();
		}
		for(Sound sound : sounds.values()){
			sound.dispose();
		}
		sounds.clear();
	}
}
