package com.jchappelle.sg;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;

public class DespawnSystem extends EntitySystem {

    private ImmutableArray<Entity> entities;
    private Engine engine;

    public void addedToEngine(Engine engine){
        entities = engine.getEntitiesFor(Family.all(TransformComponent.class).get());
        this.engine = engine;
    }

    public void update(float deltaTime){
        for(Entity entity : entities){
            if(isOutOfBounds(entity)){
                engine.removeEntity(entity);
            }
        }
    }

    private boolean isOutOfBounds(Entity entity){
        TransformComponent pc = TransformComponent.get(entity);
        return (pc.x > Gdx.graphics.getWidth() || pc.x < 0) || (pc.y > Gdx.graphics.getHeight() + 200 || pc.y < 0);
    }

}
