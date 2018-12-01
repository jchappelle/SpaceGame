package com.jchappelle.sg.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.jchappelle.sg.Constants;
import com.jchappelle.sg.components.DeathComponent;
import com.jchappelle.sg.SoundId;
import com.jchappelle.sg.components.TransformComponent;
import com.jchappelle.sg.systems.damage.DamageComponent;
import com.jchappelle.sg.systems.damage.HealthComponent;
import com.jchappelle.sg.systems.level.XpComponent;
import com.jchappelle.sg.systems.physics.BodyComponent;
import com.jchappelle.sg.systems.physics.InitialForceComponent;
import com.jchappelle.sg.systems.player.ScoreComponent;
import com.jchappelle.sg.systems.render.SpriteComponent;

class AsteroidEntityFactory implements EntityFactory {

    public Entity make(Prefab prefab){
        Entity entity = newEntity("asteroid.png", 10f, Constants.CATEGORY_ENEMY, Constants.MASK_ENEMY);
        entity.add(new InitialForceComponent(new Vector2(0, -20f)));
        entity.add(new HealthComponent(10));
        entity.add(new DamageComponent(10));
        entity.add(new ScoreComponent(10));
        entity.add(new XpComponent(5));
        entity.add(new DeathComponent(SoundId.ASTEROID_EXPLOSION, true));
        return entity;
    }

    private Entity newEntity(String spritePath, float density, short collisionCategory, short collisionMask){
        Entity entity = new Entity();
        Sprite sprite = new Sprite(new Texture(spritePath));
        entity.add(new SpriteComponent(sprite));

        TransformComponent tc = new TransformComponent(0, 0, 0, sprite.getWidth(), sprite.getHeight());
        entity.add(new BodyComponent(tc.x, tc.y, tc.width, tc.height, density, collisionCategory, collisionMask));
        entity.add(tc);
        return entity;
    }

}
