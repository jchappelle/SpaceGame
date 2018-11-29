package com.jchappelle.sg;

import com.badlogic.gdx.*;
import com.jchappelle.sg.screens.*;

import java.util.HashMap;
import java.util.Map;

public class SpaceGame extends Game implements GameManager {

	private static final ScreenId INITIAL_SCREEN = ScreenId.MAIN_MENU;

	private Map<ScreenId, Screen> screens = new HashMap<ScreenId, Screen>();
	private AppPreferences prefs;

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
	public void create () {
		prefs = new AppPreferences();

		changeScreen(INITIAL_SCREEN);
	}

	@Override
	public void dispose () {
		if (screen != null) screen.dispose();
	}
}
