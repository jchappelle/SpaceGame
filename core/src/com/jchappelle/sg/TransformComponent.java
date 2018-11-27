package com.jchappelle.sg;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;

public class TransformComponent implements Component {
    private static ComponentMapper<TransformComponent> mapper = ComponentMapper.getFor(TransformComponent.class);
    public static TransformComponent get(Entity entity){
        return mapper.get(entity);
    }

    public float x;
    public float y;
    public float rotation;
    public float width;
    public float height;
    public float scaleX = 1;
    public float scaleY = 1;
    public float originX;
    public float originY;

    public TransformComponent(){}

    public TransformComponent(float x, float y, float rotation, float width, float height){
        this.x = x;
        this.y = y;
        this.rotation = rotation;
        this.width = width;
        this.height = height;
    }
}
