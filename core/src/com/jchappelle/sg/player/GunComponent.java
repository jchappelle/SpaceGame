package com.jchappelle.sg.player;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;

public class GunComponent implements Component {
    private static ComponentMapper<GunComponent> mapper = ComponentMapper.getFor(GunComponent.class);
    public static GunComponent get(Entity entity){
        return mapper.get(entity);
    }

    long lastBulletShot;
    long coolDownMillis = 500;

}
