package com.jchappelle.sg.systems.render;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.*;
import com.jchappelle.sg.Constants;

public class PhysicsRenderSystem extends EntitySystem {

    private Engine engine;
    private SpriteBatch batch;
    private World world;
    private Box2DDebugRenderer debugRenderer;

    private boolean debug = false;

    public PhysicsRenderSystem(World world){
        this.world = world;

        debugRenderer = new Box2DDebugRenderer();
    }

    public void addedToEngine(Engine engine){
        this.engine = engine;

    }

    public void update(float deltaTime){
        if(Gdx.input.isKeyPressed(Input.Keys.F3) && Gdx.input.isKeyPressed(Input.Keys.B)){
            debug = !debug;
        }
        if(debug){
            SpriteBatch batch = getSpriteBatch();

            Matrix4 debugMatrix = batch.getProjectionMatrix().cpy().scale(Constants.PIXELS_TO_METERS, Constants.PIXELS_TO_METERS, 0);
            debugRenderer.render(world, debugMatrix);
        }

    }

    private SpriteBatch getSpriteBatch(){
        if(batch == null){
            RenderSystem renderSystem = engine.getSystem(RenderSystem.class);
            if(renderSystem != null){
                this.batch = renderSystem.batch;
            }
        }
        return batch;
    }

}
