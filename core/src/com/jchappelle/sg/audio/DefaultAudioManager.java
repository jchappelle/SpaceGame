package com.jchappelle.sg.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Json;
import com.jchappelle.sg.preferences.AppPreferences;
import com.jchappelle.sg.preferences.AppPreferencesListener;

import java.util.HashMap;
import java.util.Map;

class DefaultAudioManager implements AudioManager, AppPreferencesListener {

    private AppPreferences prefs;
    private MusicId currentMusic;
    private Map<SoundId, Sound> sounds = new HashMap<SoundId, Sound>();
    private Map<MusicId, Music> music = new HashMap<MusicId, Music>();

    public DefaultAudioManager(AppPreferences appPreferences){
        this.prefs = appPreferences;
        loadSounds();
        loadMusic();

        prefs.addListener(this);
    }

    private Sound getSound(SoundId soundId) {
        return sounds.get(soundId);
    }

    @Override
    public void playSound(SoundId soundId) {
        if(prefs.isSoundEffectsEnabled()){
            Sound sound = getSound(soundId);
            if(sound != null){
                sound.play(prefs.getSoundVolume());
            }
        }
    }

    @Override
    public void playMusic(MusicId musicId){
        if(prefs.isMusicEnabled()){
            Music musicObj = music.get(musicId);
            if(musicObj != null){
                if(currentMusic != null && currentMusic != musicId){
                    music.get(currentMusic).stop();
                }

                musicObj.setLooping(true);
                musicObj.setVolume(prefs.getMusicVolume());
                musicObj.play();
                currentMusic = musicId;
            }
        }
        currentMusic = musicId;
    }

    @Override
    public void onPreferencesChanged(AppPreferences prefs) {
        if(currentMusic != null){
            Music musicObj = music.get(currentMusic);
            musicObj.setVolume(prefs.getMusicVolume());
            if(prefs.isMusicEnabled() && !musicObj.isPlaying()){
                musicObj.play();
                musicObj.setLooping(true);
            }
            else if(!prefs.isMusicEnabled() && musicObj.isPlaying()){
                musicObj.stop();
            }
        }
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
    public void dispose(){
        for(Sound sound : sounds.values()){
            sound.dispose();
        }
        sounds.clear();

        for(Music musicObj : music.values()){
            musicObj.dispose();
        }
        music.clear();

    }
}
