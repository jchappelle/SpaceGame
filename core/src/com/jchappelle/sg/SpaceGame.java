package com.jchappelle.sg;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.jchappelle.sg.crashhandler.CrashHandler;
import com.jchappelle.sg.crashhandler.CrashHandlerFactory;
import com.jchappelle.sg.entities.EntityFactory;
import com.jchappelle.sg.entities.EntityFactoryProvider;
import com.jchappelle.sg.preferences.AppPreferences;
import com.jchappelle.sg.preferences.AppPreferencesFactory;
import com.jchappelle.sg.preferences.AppPreferencesListener;
import com.jchappelle.sg.screens.*;

import java.util.HashMap;
import java.util.Map;

class SpaceGame extends Game implements GameManager, AppPreferencesListener {

	private static final ScreenId INITIAL_SCREEN = ScreenId.MAIN_MENU;

	private Map<ScreenId, Screen> screens = new HashMap<ScreenId, Screen>();
	private AppPreferences prefs;
	private CrashHandler crashHandler;
	private Map<SoundId, Sound> sounds = new HashMap<SoundId, Sound>();
	private Map<MusicId, Music> music = new HashMap<MusicId, Music>();
	private MusicId currentMusic;
	private Entity player;

	@Override
	public void create () {
		prefs = AppPreferencesFactory.make();
		crashHandler = CrashHandlerFactory.make(this);
		loadSounds();
		loadMusic();
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
	public void changeScreen(ScreenId screenId){
		Screen screen = screens.get(screenId);
		if(screen == null){
			screen = ScreenFactory.makeScreen(screenId, this);
			screens.put(screenId, screen);
		}
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
	public void playMusic(MusicId musicId){
		if(prefs.isMusicEnabled()){
			Music musicObj = music.get(musicId);
			if(musicObj != null){
				if(currentMusic != null){
					music.get(currentMusic).stop();
				}

				musicObj.setLooping(true);
				musicObj.play();
				currentMusic = musicId;
			}
		}
	}

	@Override
	public EntityFactory getEntityFactory(){
		return EntityFactoryProvider.getEntityFactory(this);
	}

	private void loadSounds(){
		for(SoundId soundId : SoundId.values()){
			sounds.put(soundId, Gdx.audio.newSound(Gdx.files.internal(soundId.getPath())));
		}
	}

	private void loadMusic(){
		for(MusicId musicId : MusicId.values()){
			music.put(musicId, Gdx.audio.newMusic(Gdx.files.internal(musicId.getPath())));
		}
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

		for(Music musicObj : music.values()){
			musicObj.dispose();
		}
		music.clear();

	}

	@Override
	public void onPreferencesChanged(AppPreferences prefs) {
		if(currentMusic != null){
			Music musicObj = music.get(currentMusic);
			if(prefs.isMusicEnabled() && !musicObj.isPlaying()){
				musicObj.play();
				musicObj.setLooping(true);
			}
			else if(!prefs.isMusicEnabled() && musicObj.isPlaying()){
				musicObj.stop();
			}
		}
	}
}
