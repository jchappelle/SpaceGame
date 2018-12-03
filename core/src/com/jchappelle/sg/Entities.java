package com.jchappelle.sg;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.physics.box2d.World;
import com.jchappelle.sg.systems.physics.BodyComponent;
import com.jchappelle.sg.systems.physics.WorldComponent;
import com.jchappelle.sg.systems.player.PlayerComponent;

public class Entities {

    private static Entities INSTANCE;

    private World world;
    private Engine engine;

    private Entities(World world, Engine engine){
        this.world = world;
        this.engine = engine;
    }

    public static void init(World world, Engine engine){
        if(INSTANCE != null){
            throw new IllegalStateException("There can be only one!");
        }

        INSTANCE = new Entities(world, engine);
    }
    public static Entities get(){
        return INSTANCE;
    }

    public Entity getPlayer() {
        ImmutableArray<Entity> entities = engine.getEntitiesFor(Family.all(PlayerComponent.class, BodyComponent.class).get());
        for(Entity entity : entities){
            return entity;
        }
        throw new IllegalStateException("Unable to find player! Need to add PlayerComponent to the player entity.");
    }

    public Entity getGame() {
        ImmutableArray<Entity> entities = engine.getEntitiesFor(Family.all(WorldComponent.class).get());
        for(Entity entity : entities){
            return entity;
        }
        throw new IllegalStateException("Unable to find game!");
    }

}
