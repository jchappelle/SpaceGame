package com.jchappelle.sg.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Disposable;

public class MusicSystem extends EntitySystem implements Disposable {

    private Engine engine;
    private Music music;

    public void addedToEngine(Engine engine){
        this.engine = engine;

        music = Gdx.audio.newMusic(Gdx.files.internal("music/theme1.wav"));
        music.play();
        music.setLooping(true);
    }

    public void update(float deltaTime){

    }

    public void dispose(){
        music.dispose();
    }
}
