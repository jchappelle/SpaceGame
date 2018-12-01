package com.jchappelle.sg.entities;

import com.badlogic.ashley.core.Entity;

import java.util.HashMap;
import java.util.Map;

class DelegatingEntityFactory implements EntityFactory {

    private Map<Prefab, EntityFactory> factories = new HashMap<Prefab, EntityFactory>();

    public Entity make(Prefab prefab){
        EntityFactory factory = factories.get(prefab);
        if(factory != null){
            return factory.make(prefab);
        }
        return null;
    }

    public void registerFactory(Prefab prefab, EntityFactory factory){
        if(factories.containsKey(prefab)){
            throw new IllegalArgumentException("Already a factory for prefab " + prefab);
        }
        factories.put(prefab, factory);
    }
}
