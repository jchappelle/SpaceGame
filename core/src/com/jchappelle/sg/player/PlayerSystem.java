package com.jchappelle.sg.player;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.Body;
import com.jchappelle.sg.physics.BodyComponent;

public class PlayerSystem extends EntitySystem {

    float speed = 5f;
    private Body body;

    public void addedToEngine(Engine engine) {
        ImmutableArray<Entity> entities = engine.getEntitiesFor(Family.all(PlayerComponent.class, BodyComponent.class).get());
        for(Entity entity : entities){
            body = entity.getComponent(BodyComponent.class).body;
            break;
        }
    }

    public void update(float deltaTime) {
        boolean right = Gdx.input.isKeyPressed(Input.Keys.D);
        if(right){
            body.applyForceToCenter(speed, 0, true);
        }
        boolean left = Gdx.input.isKeyPressed(Input.Keys.A);
        if(left){
            body.applyForceToCenter(-speed, 0, true);
        }
        boolean up = Gdx.input.isKeyPressed(Input.Keys.W);
        if(up){
            body.applyForceToCenter(0f, speed, true);
        }
        boolean down = Gdx.input.isKeyPressed(Input.Keys.S);
        if(down){
            body.applyForceToCenter(0f, -speed, true);
        }
        boolean arrow_right = Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT);
        if(arrow_right){
            body.applyTorque(-0.1f,true);
        }
        boolean arrow_left = Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT);
        if(arrow_left){
            body.applyTorque(0.1f,true);
        }

    }
}
