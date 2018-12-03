package com.jchappelle.sg.systems.audio;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.jchappelle.sg.GameManager;
import com.jchappelle.sg.audio.MusicId;

public class MusicSystem extends EntitySystem {

    private Engine engine;
    private GameManager gameManager;

    public MusicSystem(GameManager gameManager){
        this.gameManager = gameManager;
    }

    public void addedToEngine(Engine engine){
        this.engine = engine;

        gameManager.getAudioManager().playMusic(MusicId.Theme1);
    }

    public void update(float deltaTime){

    }
}
