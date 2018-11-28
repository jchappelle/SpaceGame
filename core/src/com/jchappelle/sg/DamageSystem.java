package com.jchappelle.sg;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.jchappelle.sg.physics.CollisionListener;
import com.badlogic.gdx.utils.*;

public class DamageSystem extends EntitySystem implements CollisionListener {

    private class Collision{
        Entity entityA;
        Entity entityB;

        public Collision(Entity entityA, Entity entityB){
            this.entityA = entityA;
            this.entityB = entityB;
        }
    }

    private Engine engine;
    private Array<Collision> collisions = new Array<Collision>();

    public void addedToEngine(Engine engine){
        this.engine = engine;
    }

    public void update(float deltaTime){
        for(Collision collision : collisions){
            Entity entityA = collision.entityA;
            Entity entityB = collision.entityB;

            tryDamage(entityA, entityB);
            tryDamage(entityB, entityA);
        }
        collisions.clear();
    }

    private boolean tryDamage(Entity entityA, Entity entityB){
        HealthComponent hc = HealthComponent.get(entityA);
        DamageComponent dc = DamageComponent.get(entityB);
        if(hc != null && dc != null){
            hc.health -= dc.damage;
            if(hc.health < 0){
                hc.health = 0;
            }
            if(hc.health == 0){
                hc.deathSource = entityB;
                engine.removeEntity(entityA);
            }
            return true;
        }
        return false;
    }

    @Override
    public void onCollision(Entity entityA, Entity entityB) {
        collisions.add(new Collision(entityA, entityB));
    }
}
