package com.jchappelle.sg.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.jchappelle.sg.GameManager;
import com.jchappelle.sg.components.DeathComponent;
import com.jchappelle.sg.components.DespawnComponent;
import com.jchappelle.sg.Entities;
import com.jchappelle.sg.components.TransformComponent;
import com.jchappelle.sg.entities.Prefab;

public class DespawnSystem extends EntitySystem implements EntityListener {

    private ImmutableArray<Entity> entities;
    private Engine engine;

    private GameManager gameManager;

    public DespawnSystem(GameManager gameManager){
        this.gameManager = gameManager;
    }

    public void addedToEngine(Engine engine){
        entities = engine.getEntitiesFor(Family.all(TransformComponent.class).get());
        this.engine = engine;
        this.engine.addEntityListener(this);
    }

    public void update(float deltaTime){
        for(Entity entity : entities){
            DespawnComponent dc = DespawnComponent.get(entity);
            if(dc != null){
                dc.timer += deltaTime;
                if(dc.timer > dc.timeToLive){
                    engine.removeEntity(entity);
                }
            }
            if(isOutOfBounds(entity)){
                engine.removeEntity(entity);
            }
        }
    }

    private boolean isOutOfBounds(Entity entity){
        TransformComponent pc = TransformComponent.get(entity);
        return (pc.x > Gdx.graphics.getWidth() || pc.x < 0) || (pc.y > Gdx.graphics.getHeight() + 200 || pc.y < 0);
    }

    @Override
    public void entityAdded(Entity entity) {

    }

    @Override
    public void entityRemoved(Entity entity) {
        DeathComponent dc = DeathComponent.get(entity);
        if(dc != null){
            if(dc.explode){
                TransformComponent tc = TransformComponent.get(entity);
                Entity explosion = gameManager.getEntityFactory().make(Prefab.EXPLOSION, tc.copy());
                this.engine.addEntity(explosion);
            }
        }
    }
}
