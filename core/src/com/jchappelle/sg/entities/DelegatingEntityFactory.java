package com.jchappelle.sg.entities;

import com.badlogic.ashley.core.Entity;
import com.jchappelle.sg.components.TransformComponent;

import java.util.HashMap;
import java.util.Map;

class DelegatingEntityFactory implements EntityFactory {

    private Map<String, EntityFactory> factories = new HashMap<String, EntityFactory>();

    public Entity make(String prefab){
        return make(prefab, new TransformComponent());
    }

    public Entity make(String prefab, TransformComponent transform){
        EntityFactory factory = factories.get(prefab);
        if(factory != null){
            return factory.make(prefab, transform);
        }
        return null;
    }

    public void registerFactory(String prefab, EntityFactory factory){
        if(factories.containsKey(prefab)){
            throw new IllegalArgumentException("Already a factory for prefab " + prefab);
        }
        factories.put(prefab, factory);
    }
}
