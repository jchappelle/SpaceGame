package com.jchappelle.sg;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;

public class SpawnComponent implements Component {
    private static ComponentMapper<SpawnComponent> mapper = ComponentMapper.getFor(SpawnComponent.class);
    public static SpawnComponent get(Entity entity){
        return mapper.get(entity);
    }

    public SoundId soundEffect;

    public SpawnComponent(SoundId soundEffect){
        this.soundEffect = soundEffect;
    }
}
