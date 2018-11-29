package com.jchappelle.sg.systems.level;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.jchappelle.sg.Entities;

public class LevelSystem extends EntitySystem implements EntityListener {

    private Engine engine;

    public void addedToEngine(Engine engine){
        this.engine = engine;
        engine.addEntityListener(this);
    }

    @Override
    public void entityAdded(Entity entity) {

    }

    @Override
    public void entityRemoved(Entity entity) {
        XpComponent xpc = XpComponent.get(entity);
        if(xpc != null){
            Entity game = Entities.get().getGame();
            LevelComponent lc = LevelComponent.get(game);
            lc.totalXp += xpc.xp;
            lc.level = lc.totalXp / 10 + 1;
        }
    }
}
