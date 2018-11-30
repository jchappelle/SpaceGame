package com.jchappelle.sg;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;

public class DeathComponent implements Component {
    private static ComponentMapper<DeathComponent> mapper = ComponentMapper.getFor(DeathComponent.class);
    public static DeathComponent get(Entity entity){
        return mapper.get(entity);
    }

    public SoundId soundEffect;
    public boolean explode;

    public DeathComponent(SoundId soundEffect){
        this.soundEffect = soundEffect;
    }

    public DeathComponent(SoundId soundEffect, boolean explode){
        this.soundEffect = soundEffect;
        this.explode = explode;
    }

}
