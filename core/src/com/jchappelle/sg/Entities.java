package com.jchappelle.sg;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.jchappelle.sg.audio.SoundId;
import com.jchappelle.sg.components.DespawnComponent;
import com.jchappelle.sg.components.SpawnComponent;
import com.jchappelle.sg.components.TransformComponent;
import com.jchappelle.sg.systems.damage.DamageComponent;
import com.jchappelle.sg.systems.damage.HealthComponent;
import com.jchappelle.sg.systems.physics.BodyComponent;
import com.jchappelle.sg.systems.physics.WorldComponent;
import com.jchappelle.sg.systems.player.GunComponent;
import com.jchappelle.sg.systems.player.PlayerComponent;
import com.jchappelle.sg.systems.render.AnimationComponent;
import com.jchappelle.sg.systems.render.SpriteComponent;

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
