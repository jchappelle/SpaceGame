package com.jchappelle.sg.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.jchappelle.sg.Constants;
import com.jchappelle.sg.GameManager;
import com.jchappelle.sg.audio.SoundId;
import com.jchappelle.sg.components.SpawnComponent;
import com.jchappelle.sg.components.SpawnSourceComponent;
import com.jchappelle.sg.components.TransformComponent;
import com.jchappelle.sg.systems.damage.DamageComponent;
import com.jchappelle.sg.systems.damage.HealthComponent;
import com.jchappelle.sg.systems.physics.BodyComponent;
import com.jchappelle.sg.systems.render.SpriteComponent;

class BulletEntityFactory implements EntityFactory {

    private GameManager gameManager;

    public BulletEntityFactory(GameManager gameManager){
        this.gameManager = gameManager;
    }

    @Override
    public Entity make(String prefabId) {
        return make(prefabId, new TransformComponent());
    }

    @Override
    public Entity make(String prefabId, TransformComponent tc) {
        Entity entity = new Entity();
        Sprite sprite = new Sprite(new Texture("bullet.png"));
        entity.add(new SpriteComponent(sprite));

        tc.width = sprite.getWidth();
        tc.height = sprite.getHeight();

        BodyComponent bc = new BodyComponent();
        bc.initialX = tc.x;
        bc.initialY = tc.y;
        bc.width = tc.width;
        bc.height = tc.height;
        bc.density = 10f;
        bc.collisionMask = Constants.MASK_PLAYER_BULLET;
        bc.collisionCategory = Constants.CATEGORY_PLAYER_BULLET;
        bc.initialForce = new Vector2(0, 20f);
        entity.add(bc);
        entity.add(tc);

        entity.add(new HealthComponent(1));
        entity.add(new DamageComponent(100));
        entity.add(new SpawnComponent(SoundId.BULLET));
        entity.add(new SpawnSourceComponent(gameManager.getPlayer()));
        return entity;
    }
}
