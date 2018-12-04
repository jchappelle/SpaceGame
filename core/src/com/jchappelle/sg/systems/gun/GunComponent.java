package com.jchappelle.sg.systems.gun;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.jchappelle.sg.entities.Prefab;

public class GunComponent implements Component {
    private static ComponentMapper<GunComponent> mapper = ComponentMapper.getFor(GunComponent.class);
    public static GunComponent get(Entity entity){
        return mapper.get(entity);
    }

    public float speed = 10f;
    public String prefabId = Prefab.BULLET;
    long lastBulletShot;
    long coolDownMillis = 500;

}
