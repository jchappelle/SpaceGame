package com.jchappelle.sg.systems.render;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;

public class BlinkingComponent implements Component {
    private static ComponentMapper<BlinkingComponent> mapper = ComponentMapper.getFor(BlinkingComponent.class);
    public static BlinkingComponent get(Entity entity){
        return mapper.get(entity);
    }

    public float blinkTimer;
    public float timer;
    public float timeToLive;
    public float speed = 1;

    public BlinkingComponent(){

    }

    public BlinkingComponent(float timeToLive, float speed){
        this.timeToLive = timeToLive;
        this.speed = speed;
    }

}
