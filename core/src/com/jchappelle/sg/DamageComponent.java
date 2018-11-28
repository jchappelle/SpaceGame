package com.jchappelle.sg;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;

public class DamageComponent implements Component {
    private static ComponentMapper<DamageComponent> mapper = ComponentMapper.getFor(DamageComponent.class);
    public static DamageComponent get(Entity entity){
        return mapper.get(entity);
    }

    public int damage;

    public DamageComponent(int damage){
        this.damage = damage;
    }
}
