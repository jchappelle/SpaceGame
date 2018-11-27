package com.jchappelle.sg.physics;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.jchappelle.sg.Constants;
import com.jchappelle.sg.TransformComponent;

public class PhysicsSystem extends EntitySystem {

    private ImmutableArray<Entity> entities;
    private World world;
    private Engine engine;
    private float accumulator = 0;

    private static int VELOCITY_ITERATIONS = 6;
    private static int POSITION_ITERATIONS = 2;
    private static float TIME_STEP = 1/60F;

    public PhysicsSystem(World world){
        this.world = world;
    }

    public void addedToEngine(Engine engine){
        this.engine = engine;
        this.entities = engine.getEntitiesFor(Family.all(BodyComponent.class, TransformComponent.class).get());
        setupScreenBounds();
    }

    private void setupScreenBounds(){
        //Bottom
        createWall(0, 0, Gdx.graphics.getWidth(), 1);
        //Left
        createWall(0, 0, 1, Gdx.graphics.getHeight());
        //Right
        createWall(Gdx.graphics.getWidth(), 0, 1, Gdx.graphics.getHeight());
        //Top
        createWall(0, Gdx.graphics.getHeight(), Gdx.graphics.getWidth(), 1);
    }

    private void createWall(float x, float y, float width, float height){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(new Vector2(x/ Constants.PIXELS_TO_METERS, y/ Constants.PIXELS_TO_METERS));
        Body body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/ Constants.PIXELS_TO_METERS, height/ Constants.PIXELS_TO_METERS);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.0f;
        fixtureDef.filter.maskBits = Constants.MASK_BOUNDS;
        fixtureDef.filter.categoryBits = Constants.CATEGORY_BOUNDS;

        body.createFixture(fixtureDef);
        shape.dispose();
    }



    public void update(float deltaTime) {
        doPhysicsStep(deltaTime);

        for(Entity entity : entities){
            updateTransform(entity);
        }
    }

    private void doPhysicsStep(float deltaTime) {
        // fixed time step
        // max frame time to avoid spiral of death (on slow devices)
        float frameTime = Math.min(deltaTime, 0.25f);
        accumulator += frameTime;
        while (accumulator >= TIME_STEP) {
            world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
            accumulator -= TIME_STEP;
        }
    }

    private void updateTransform(Entity entity){
        Body body = BodyComponent.get(entity).body;
        TransformComponent tc = TransformComponent.get(entity);

        tc.x = (body.getPosition().x * Constants.PIXELS_TO_METERS);
        tc.y = (body.getPosition().y * Constants.PIXELS_TO_METERS);

        tc.rotation = ((float)Math.toDegrees(body.getAngle()));
    }
}
