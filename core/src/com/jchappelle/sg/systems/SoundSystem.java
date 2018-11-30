package com.jchappelle.sg.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.jchappelle.sg.DeathComponent;
import com.jchappelle.sg.GameManager;
import com.jchappelle.sg.SpawnComponent;

public class SoundSystem extends EntitySystem implements EntityListener {

    private Engine engine;
    private GameManager gameManager;

    public SoundSystem(GameManager gameManager){
        this.gameManager = gameManager;
    }

    public void addedToEngine(Engine engine){
        this.engine = engine;
        this.engine.addEntityListener(this);
    }

    @Override
    public void entityAdded(Entity entity) {
        SpawnComponent spawnComponent = SpawnComponent.get(entity);
        if(spawnComponent != null && spawnComponent.soundEffect != null){
            gameManager.playSound(spawnComponent.soundEffect);
        }
    }

    @Override
    public void entityRemoved(Entity entity) {
        DeathComponent deathComponent = DeathComponent.get(entity);
        if(deathComponent != null && deathComponent.soundEffect != null){
            gameManager.playSound(deathComponent.soundEffect);
        }
    }
}
