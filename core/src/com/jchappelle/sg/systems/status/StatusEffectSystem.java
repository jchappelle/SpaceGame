package com.jchappelle.sg.systems.status;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;

public class StatusEffectSystem extends EntitySystem {

    private Engine engine;
    private ImmutableArray<Entity> entities;

    public void addedToEngine(Engine engine){
        this.engine = engine;
        entities = engine.getEntitiesFor(Family.one(InvincibilityComponent.class).get());
    }

    public void update(float deltaTime){
        for(Entity entity : entities){
            InvincibilityComponent ic = InvincibilityComponent.get(entity);
            if(ic != null){
                ic.timeElapsed += deltaTime;
                if(ic.timeElapsed > ic.timeToLive){
                    entity.remove(InvincibilityComponent.class);
                }
            }
        }
    }
}
