package com.jchappelle.sg.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.World;
import com.jchappelle.sg.Constants;
import com.jchappelle.sg.GameManager;
import com.jchappelle.sg.components.TransformComponent;
import com.jchappelle.sg.systems.damage.DamageComponent;
import com.jchappelle.sg.systems.damage.HealthComponent;
import com.jchappelle.sg.systems.physics.BodyComponent;
import com.jchappelle.sg.systems.player.GunComponent;
import com.jchappelle.sg.systems.player.PlayerComponent;
import com.jchappelle.sg.systems.render.SpriteComponent;

class PlayerShipEntityFactory implements EntityFactory {

    private GameManager gameManager;
    private World world;

    public PlayerShipEntityFactory(GameManager gameManager){
        this.gameManager = gameManager;
    }

    public Entity make(Prefab prefab){
        Entity entity = newEntity("ship.png", 0, 0, 10f, Constants.CATEGORY_PLAYER, Constants.MASK_PLAYER);
        entity.add(new PlayerComponent());
        entity.add(new GunComponent());
        entity.add(new HealthComponent(30));
        entity.add(new DamageComponent(100));
        return entity;
    }

    private Entity newEntity(String spritePath, float x, float y, float density, short collisionCategory, short collisionMask){
        Entity entity = new Entity();
        Sprite sprite = new Sprite(new Texture(spritePath));
        sprite.setOriginCenter();
        sprite.setOrigin(sprite.getWidth(), sprite.getHeight());
        entity.add(new SpriteComponent(sprite));

        TransformComponent tc = new TransformComponent(x, y, 0, sprite.getWidth(), sprite.getHeight());
        entity.add(new BodyComponent(tc.x, tc.y, tc.width, tc.height, density, collisionCategory, collisionMask));
        entity.add(tc);
        return entity;
    }

}
