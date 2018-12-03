package com.jchappelle.sg.systems.status;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;

public class InvincibilityComponent implements Component {
    private static ComponentMapper<InvincibilityComponent> mapper = ComponentMapper.getFor(InvincibilityComponent.class);
    public static InvincibilityComponent get(Entity entity){
        return mapper.get(entity);
    }

    public float time;
    public float timeElapsed;

    public InvincibilityComponent(){}

    public InvincibilityComponent(float time){
        this.time = time;
    }
}
