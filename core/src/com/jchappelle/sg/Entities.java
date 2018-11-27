package com.jchappelle.sg;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.jchappelle.sg.physics.BodyComponent;
import com.jchappelle.sg.physics.WorldComponent;
import com.jchappelle.sg.player.GunComponent;
import com.jchappelle.sg.player.PlayerComponent;
import com.jchappelle.sg.render.SpriteComponent;

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

    public Entity newShip(){
        Entity entity = newEntity("ship.png", 0, 0, 10f);
        entity.add(new PlayerComponent());
        entity.add(new GunComponent());
        return entity;
    }

    public Entity newBullet(float x, float y){
        Entity entity = newEntity("bullet.png", x, y, 10f);
        BodyComponent bc = BodyComponent.get(entity);
        bc.body.applyForceToCenter(new Vector2(0, 1f), true);
        entity.add(bc);
        return entity;
    }

    public Entity newAsteroid(float x, float y){
        Entity entity = newEntity("asteroid.png", x, y, 10f);

        BodyComponent bc = BodyComponent.get(entity);
        bc.body.applyForceToCenter(new Vector2(0, -10f), true);
        entity.add(bc);
        return entity;
    }

    private Entity newEntity(String spritePath, float x, float y, float density){
        Entity entity = new Entity();
        Sprite sprite = new Sprite(new Texture(spritePath));
        sprite.setOriginCenter();
        sprite.setOrigin(sprite.getWidth(), sprite.getHeight());
        entity.add(new SpriteComponent(sprite));

        TransformComponent tc = new TransformComponent(x, y, 0, sprite.getWidth(), sprite.getHeight());
        entity.add(new BodyComponent(world, tc.x, tc.y, tc.width, tc.height, density));
        entity.add(tc);
        return entity;
    }

}
