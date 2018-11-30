package com.jchappelle.sg;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;

public class DespawnComponent implements Component {
    private static ComponentMapper<DespawnComponent> mapper = ComponentMapper.getFor(DespawnComponent.class);
    public static DespawnComponent get(Entity entity){
        return mapper.get(entity);
    }

    public float timer;
    public float timeToLive;

    public DespawnComponent(float timeToLive){
        this.timeToLive = timeToLive;
    }
}
