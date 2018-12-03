package com.jchappelle.sg.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.jchappelle.sg.Constants;
import com.jchappelle.sg.components.DeathComponent;
import com.jchappelle.sg.audio.SoundId;
import com.jchappelle.sg.components.TransformComponent;
import com.jchappelle.sg.systems.damage.DamageComponent;
import com.jchappelle.sg.systems.damage.HealthComponent;
import com.jchappelle.sg.systems.level.XpComponent;
import com.jchappelle.sg.systems.physics.BodyComponent;
import com.jchappelle.sg.systems.player.ScoreComponent;
import com.jchappelle.sg.systems.render.SpriteComponent;

class AsteroidEntityFactory implements EntityFactory {

    public Entity make(String prefabId, TransformComponent transform){
        return make(prefabId);
    }

    public Entity make(String prefab){
        Entity entity = new Entity();
        Sprite sprite = new Sprite(new Texture("asteroid.png"));
        entity.add(new SpriteComponent(sprite));
        TransformComponent tc = new TransformComponent(0, 0, 0, sprite.getWidth(), sprite.getHeight());

        BodyComponent bc = new BodyComponent();
        bc.initialX = tc.x;
        bc.initialY = tc.y;
        bc.width = tc.width;
        bc.height = tc.height;
        bc.density = 10f;
        bc.collisionCategory = Constants.CATEGORY_ENEMY;
        bc.collisionMask = Constants.MASK_ENEMY;
        bc.initialForce = new Vector2(0, -20f);
        entity.add(bc);
        entity.add(tc);

        entity.add(new HealthComponent(10));
        entity.add(new DamageComponent(10));
        entity.add(new ScoreComponent(10));
        entity.add(new XpComponent(5));
        entity.add(new DeathComponent(SoundId.ASTEROID_EXPLOSION, true));
        return entity;
    }

}
