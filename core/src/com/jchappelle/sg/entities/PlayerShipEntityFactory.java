package com.jchappelle.sg.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.jchappelle.sg.Constants;
import com.jchappelle.sg.GameManager;
import com.jchappelle.sg.components.TransformComponent;
import com.jchappelle.sg.systems.damage.DamageComponent;
import com.jchappelle.sg.systems.damage.HealthComponent;
import com.jchappelle.sg.systems.gun.MultishotComponent;
import com.jchappelle.sg.systems.physics.BodyComponent;
import com.jchappelle.sg.systems.gun.GunComponent;
import com.jchappelle.sg.systems.player.PlayerComponent;
import com.jchappelle.sg.systems.render.SpriteComponent;

class PlayerShipEntityFactory implements EntityFactory {

    private GameManager gameManager;

    public PlayerShipEntityFactory(GameManager gameManager){
        this.gameManager = gameManager;
    }

    public Entity make(String prefabId){
        return make(prefabId, new TransformComponent());
    }

    public Entity make(String prefabId, TransformComponent tc){
        Entity entity = new Entity();
        Sprite sprite = new Sprite(new Texture("ship.png"));
        sprite.setOrigin(sprite.getWidth(), sprite.getHeight());
        entity.add(new SpriteComponent(sprite));
        tc = new TransformComponent(0, 0, 0, sprite.getWidth(), sprite.getHeight());

        BodyComponent bc = new BodyComponent();
        bc.initialX = tc.x;
        bc.initialY = tc.y;
        bc.height = tc.height;
        bc.width = tc.width;
        bc.density = 10f;
        bc.collisionCategory = Constants.CATEGORY_PLAYER;
        bc.collisionMask = Constants.MASK_PLAYER;
        bc.fixedRotation = true;
        entity.add(tc);
        entity.add(bc);
        entity.add(new PlayerComponent());
        entity.add(new GunComponent());
        entity.add(new HealthComponent(30));
        entity.add(new DamageComponent(100));
        entity.add(new MultishotComponent(7));
        return entity;
    }
}
