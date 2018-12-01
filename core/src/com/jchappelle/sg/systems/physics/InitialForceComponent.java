package com.jchappelle.sg.systems.physics;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;

public class InitialForceComponent implements Component {
    private static ComponentMapper<InitialForceComponent> mapper = ComponentMapper.getFor(InitialForceComponent.class);
    public static InitialForceComponent get(Entity entity){
        return mapper.get(entity);
    }

    public Vector2 force;

    public InitialForceComponent(Vector2 force){
        this.force = force;
    }

}
