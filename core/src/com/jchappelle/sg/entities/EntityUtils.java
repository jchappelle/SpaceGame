package com.jchappelle.sg.entities;

import com.badlogic.ashley.core.Entity;
import com.jchappelle.sg.components.TransformComponent;

public final class EntityUtils {

    public static void setPosition(Entity entity, float x, float y){
        TransformComponent tc = TransformComponent.get(entity);
        if(tc != null){
            tc.x = x;
            tc.y = y;
        }
    }
}
