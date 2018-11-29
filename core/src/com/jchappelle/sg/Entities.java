package com.jchappelle.sg;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.jchappelle.sg.components.TransformComponent;
import com.jchappelle.sg.systems.damage.DamageComponent;
import com.jchappelle.sg.systems.damage.HealthComponent;
import com.jchappelle.sg.systems.level.XpComponent;
import com.jchappelle.sg.systems.physics.BodyComponent;
import com.jchappelle.sg.systems.physics.WorldComponent;
import com.jchappelle.sg.systems.player.GunComponent;
import com.jchappelle.sg.systems.player.PlayerComponent;
import com.jchappelle.sg.systems.player.ScoreComponent;
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

    public Entity newShip(){
        Entity entity = newEntity("ship.png", 0, 0, 10f, Constants.CATEGORY_PLAYER, Constants.MASK_PLAYER);
        entity.add(new PlayerComponent());
        entity.add(new GunComponent());
        entity.add(new HealthComponent(30));
        entity.add(new DamageComponent(100));
        return entity;
    }

    public Entity newBullet(float x, float y){
        Entity entity = newEntity("bullet.png", x, y, 10f, Constants.CATEGORY_PLAYER_BULLET, Constants.MASK_PLAYER_BULLET);
        BodyComponent bc = BodyComponent.get(entity);
        bc.body.applyForceToCenter(new Vector2(0, 20f), true);
        entity.add(bc);

        entity.add(new HealthComponent(1));
        entity.add(new DamageComponent(100));
        return entity;
    }

    public Entity newAsteroid(float x, float y){
        Entity entity = newEntity("asteroid.png", x, y, 10f, Constants.CATEGORY_ENEMY, Constants.MASK_ENEMY);

        BodyComponent bc = BodyComponent.get(entity);
        bc.body.applyForceToCenter(new Vector2(0, -20f), true);
        entity.add(bc);

        entity.add(new HealthComponent(10));
        entity.add(new DamageComponent(10));
        entity.add(new ScoreComponent(10));
        entity.add(new XpComponent(5));
        return entity;
    }

    private Entity newEntity(String spritePath, float x, float y, float density, short collisionCategory, short collisionMask){
        Entity entity = new Entity();
        Sprite sprite = new Sprite(new Texture(spritePath));
        sprite.setOriginCenter();
        sprite.setOrigin(sprite.getWidth(), sprite.getHeight());
        entity.add(new SpriteComponent(sprite));

        TransformComponent tc = new TransformComponent(x, y, 0, sprite.getWidth(), sprite.getHeight());
        entity.add(new BodyComponent(world, tc.x, tc.y, tc.width, tc.height, density, collisionCategory, collisionMask));
        entity.add(tc);
        return entity;
    }

}
