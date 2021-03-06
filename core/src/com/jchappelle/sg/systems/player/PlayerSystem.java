package com.jchappelle.sg.systems.player;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.Body;
import com.jchappelle.sg.GameManager;
import com.jchappelle.sg.screens.ScreenId;
import com.jchappelle.sg.systems.physics.BodyComponent;
import com.jchappelle.sg.systems.physics.CollisionListener;

public class PlayerSystem extends EntitySystem implements CollisionListener {

    float speed = 5f;
    private Body body;
    private Engine engine;
    private GameManager gameManager;

    public PlayerSystem(GameManager gameManager){
        this.gameManager = gameManager;
    }

    public void addedToEngine(Engine engine) {
        this.engine = engine;
    }

    public void update(float deltaTime) {
        Body body = getPlayerBody();
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
        boolean escape = Gdx.input.isKeyPressed(Input.Keys.ESCAPE);
        if(escape){
            gameManager.changeScreen(ScreenId.PREFERENCES);
        }

    }

    private Body getPlayerBody(){
        if (body == null) {
            ImmutableArray<Entity> entities = engine.getEntitiesFor(Family.all(PlayerComponent.class, BodyComponent.class).get());
            for(Entity entity : entities){
                body = entity.getComponent(BodyComponent.class).body;
                break;
            }
        }
        return body;
    }

    @Override
    public void onCollision(Entity entityA, Entity entityB) {
    }
}
