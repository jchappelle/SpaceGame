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
import com.jchappelle.sg.systems.physics.InitialForceComponent;
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
        entity.add(new InitialForceComponent(new Vector2(0, 20f)));
        entity.add(new HealthComponent(1));
        entity.add(new DamageComponent(100));
        entity.add(new SpawnComponent(SoundId.BULLET));
        return entity;
    }

    //TODO: Move to asset manager
    private static Texture explosionSheet = new Texture(Gdx.files.internal("explosion.png"));

    public Entity newExplosion(float x, float y ){
        Entity entity = new Entity();

        int FRAME_COLS = 3;
        int FRAME_ROWS = 3;
        // Use the split utility method to create a 2D array of TextureRegions. This is
        // possible because this sprite sheet contains frames of equal size and they are
        // all aligned.
        TextureRegion[][] tmp = TextureRegion.split(explosionSheet,
                explosionSheet.getWidth() / FRAME_COLS,
                explosionSheet.getHeight() / FRAME_ROWS);

        // Place the regions into a 1D array in the correct order, starting from the top
        // left, going across first. The Animation constructor requires a 1D array.
        TextureRegion[] frames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                frames[index++] = tmp[i][j];
            }
        }

        float duration = 1/15f;
        entity.add(new AnimationComponent(new Animation<TextureRegion>(duration, frames)));
        entity.add(new DespawnComponent(duration * 4));
        entity.add(new TransformComponent(x, y, 0, 32, 32));
        entity.add(new SpawnComponent(SoundId.ASTEROID_EXPLOSION));
        return entity;
    }

    private Entity newEntity(String spritePath, float x, float y, float density, short collisionCategory, short collisionMask){
        Entity entity = new Entity();
        Sprite sprite = new Sprite(new Texture(spritePath));
        entity.add(new SpriteComponent(sprite));

        TransformComponent tc = new TransformComponent(x, y, 0, sprite.getWidth(), sprite.getHeight());
        entity.add(new BodyComponent(tc.x, tc.y, tc.width, tc.height, density, collisionCategory, collisionMask));
        entity.add(tc);
        return entity;
    }

}
