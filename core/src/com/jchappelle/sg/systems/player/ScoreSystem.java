package com.jchappelle.sg.systems.player;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.jchappelle.sg.Entities;
import com.jchappelle.sg.systems.damage.HealthComponent;

public class ScoreSystem extends EntitySystem implements EntityListener {

    private Engine engine;

    public void addedToEngine(Engine engine){
        this.engine = engine;
        this.engine.addEntityListener(this);
    }

    public void update(float deltaTime){
    }

    @Override
    public void entityAdded(Entity entity) {

    }

    @Override
    public void entityRemoved(Entity entity) {
        HealthComponent hc = HealthComponent.get(entity);
        if(hc != null && hc.deathSource != null){//For now assume all sources are player
            ScoreComponent sc = ScoreComponent.get(entity);
            if(sc != null){
                Entity player = Entities.get().getPlayer();
                PlayerComponent pc = PlayerComponent.get(player);
                pc.score += sc.score;
            }
        }
    }
}
