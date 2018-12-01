package com.jchappelle.sg;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.*;
import com.jchappelle.sg.audio.AudioManager;
import com.jchappelle.sg.audio.AudioManagerFactory;
import com.jchappelle.sg.crashhandler.CrashHandler;
import com.jchappelle.sg.crashhandler.CrashHandlerFactory;
import com.jchappelle.sg.entities.EntityFactory;
import com.jchappelle.sg.entities.EntityFactoryProvider;
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

	private Entity player;
	private AudioManager audioManager;
	private ScreenId currentScreen;
	private ScreenId lastScreen;

	@Override
	public void create () {
		prefs = AppPreferencesFactory.make();
		crashHandler = CrashHandlerFactory.make(this);
		audioManager = AudioManagerFactory.make(this);
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

	@Override
	public boolean changeToPreviousScreen(){
		if (lastScreen == null) {
			return false;
		}
		else{
			changeScreen(lastScreen);
			return true;
		}
	}

	@Override
	public void changeScreen(ScreenId screenId){
		Screen screen = screens.get(screenId);
		if(screen == null){
			screen = ScreenFactory.makeScreen(screenId, this);
			screens.put(screenId, screen);
		}
		lastScreen = currentScreen;
		currentScreen = screenId;

		setScreen(screen);
	}

	@Override
	public void setPlayer(Entity player){
		this.player = player;
	}

	@Override
	public Entity getPlayer(){
		return player;
	}

	@Override
	public AppPreferences getPreferences(){
		return prefs;
	}


	@Override
	public EntityFactory getEntityFactory(){
		return EntityFactoryProvider.getEntityFactory(this);
	}

	@Override
	public AudioManager getAudioManager(){
		return audioManager;
	}

	@Override
	public void dispose () {
		if (screen != null) {
			screen.dispose();
		}
		audioManager.dispose();
	}

}
