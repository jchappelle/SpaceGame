package com.jchappelle.sg.systems.physics;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class BodyComponent implements Component {
    private static ComponentMapper<BodyComponent> mapper = ComponentMapper.getFor(BodyComponent.class);
    public static BodyComponent get(Entity entity){
        return mapper.get(entity);
    }

    public Body body;

    public float initialX;
    public float initialY;
    public float density;
    public short collisionCategory;
    public short collisionMask;
    public float width;
    public float height;
    public Vector2 linearVelocity;
    public Vector2 initialForce;
}
