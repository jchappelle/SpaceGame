package com.jchappelle.sg.systems.physics;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;

public class WorldComponent implements Component, Disposable {
    private static ComponentMapper<WorldComponent> mapper = ComponentMapper.getFor(WorldComponent.class);
    public static WorldComponent get(Entity entity){
        return mapper.get(entity);
    }

    public World world;

    public WorldComponent(){
        world = new World(new Vector2(0, 0), true);
    }

    public void dispose(){

    }
}
