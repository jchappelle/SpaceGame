package com.jchappelle.sg.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;

public class SpawnSourceComponent implements Component {
    private static ComponentMapper<SpawnSourceComponent> mapper = ComponentMapper.getFor(SpawnSourceComponent.class);
    public static SpawnSourceComponent get(Entity entity){
        return mapper.get(entity);
    }

    public Entity entity;

    public SpawnSourceComponent(Entity entity){
        this.entity = entity;
    }
}
