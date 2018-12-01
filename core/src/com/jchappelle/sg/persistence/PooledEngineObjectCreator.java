package com.jchappelle.sg.persistence;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;

public class PooledEngineObjectCreator implements EngineObjectCreator{

    private PooledEngine engine;

    public PooledEngineObjectCreator(PooledEngine engine){
        this.engine = engine;
    }

    @Override
    public Entity createEntity() {
        return engine.createEntity();
    }

    @Override
    public <T extends Component> T createComponent(Class<T> type) {
        return engine.createComponent(type);
    }
}
