package com.jchappelle.sg.physics;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Queue;

import java.util.ArrayList;
import java.util.List;

public class CollisionSystem extends EntitySystem implements ContactListener, EntityListener {

    private class Collision{
        Entity entityA;
        Entity entityB;

        public Collision(Entity entityA, Entity entityB){
            this.entityA = entityA;
            this.entityB = entityB;
        }
    }
    private World world;
    private Engine engine;
    private List<Collision> collisions = new ArrayList<Collision>();
    private List<CollisionListener> collisionListeners = new ArrayList<CollisionListener>();

    public CollisionSystem(World world){
        this.world = world;

        this.world.setContactListener(this);

    }

    public void addedToEngine(Engine engine){
        this.engine = engine;

        this.engine.addEntityListener(this);
        //May need to update this list if we are going to add/remove systems that are CollisionListeners
        for(EntitySystem system : engine.getSystems()){
            if(system instanceof CollisionListener){
                collisionListeners.add((CollisionListener)system);
            }
        }
    }

    public void update(float deltaTime) {
        if(collisions.size() > 0){
            for(Collision c : collisions){
                for(CollisionListener l : collisionListeners){
                    l.onCollision(c.entityA, c.entityB);
                }
            }
        }
        collisions.clear();
    }

    @Override
    public void beginContact(Contact contact) {
        Entity entityA = getEntity(contact.getFixtureA());
        Entity entityB = getEntity(contact.getFixtureB());
        if(entityA == null || entityB == null){
            return;
        }
        collisions.add(new Collision(entityA, entityB));
    }

    private Entity getEntity(Fixture fixture){
        Object userData = fixture.getBody().getUserData();
        if(userData instanceof Entity){
            return (Entity)userData;
        }
        return null;
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    @Override
    public void entityAdded(Entity entity) {
        BodyComponent bc = BodyComponent.get(entity);
        if(bc != null){
            bc.body.setUserData(entity);
        }
    }

    @Override
    public void entityRemoved(Entity entity) {
    }


}
