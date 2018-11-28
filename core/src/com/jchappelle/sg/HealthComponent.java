package com.jchappelle.sg;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;

public class HealthComponent implements Component {
    private static ComponentMapper<HealthComponent> mapper = ComponentMapper.getFor(HealthComponent.class);
    public static HealthComponent get(Entity entity){
        return mapper.get(entity);
    }

    public int maxHealth;
    public int health;

    public HealthComponent(int health){
        this.health = health;
        this.maxHealth = maxHealth;
    }
}
