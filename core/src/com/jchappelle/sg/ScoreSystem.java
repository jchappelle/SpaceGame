package com.jchappelle.sg;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.jchappelle.sg.player.PlayerComponent;

public class ScoreSystem extends EntitySystem implements EntityListener {

    private Engine engine;

    public void addedToEngine(Engine engine){
        this.engine = engine;
        this.engine.addEntityListener(this);
    }

    int count = 0;
    public void update(float deltaTime){
        count++;
        if(count % 120 == 0){
            Entity player = Entities.get().getPlayer();
            PlayerComponent pc = PlayerComponent.get(player);

            System.out.println("Score is " + pc.score);
        }
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
