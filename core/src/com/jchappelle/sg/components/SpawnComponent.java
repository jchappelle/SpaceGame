package com.jchappelle.sg.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.jchappelle.sg.audio.SoundId;

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
