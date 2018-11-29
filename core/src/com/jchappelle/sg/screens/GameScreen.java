package com.jchappelle.sg.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.*;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.jchappelle.sg.*;
import com.jchappelle.sg.enemies.AsteroidSystem;
import com.jchappelle.sg.physics.CollisionSystem;
import com.jchappelle.sg.physics.PhysicsSystem;
import com.jchappelle.sg.physics.WorldComponent;
import com.jchappelle.sg.player.GunSystem;
import com.jchappelle.sg.player.PlayerSystem;
import com.jchappelle.sg.render.PhysicsRenderSystem;
import com.jchappelle.sg.render.RenderSystem;

class GameScreen extends ScreenAdapter {

    private World world;
    private Engine engine;
    private GameManager gameManager;

    public GameScreen(GameManager gameManager){
        this.gameManager = gameManager;
    }

    @Override
    public void show(){
        engine = new Engine();
        Entity gameEntity = new Entity();
        WorldComponent worldComponent = new WorldComponent();
        world = worldComponent.world;
        gameEntity.add(worldComponent);
        InputProcessor inputProcessor = new InputMultiplexer();
        gameEntity.add(new InputProcessorComponent(inputProcessor));
        Gdx.input.setInputProcessor(inputProcessor);
        engine.addEntity(gameEntity);

        Entities.init(world, engine);

        engine.addEntity(Entities.get().newShip());
        engine.addSystem(new RenderSystem());
        engine.addSystem(new PhysicsSystem(world));
        engine.addSystem(new PhysicsRenderSystem(world));
        engine.addSystem(new PlayerSystem());
        engine.addSystem(new GunSystem());
        engine.addSystem(new AsteroidSystem());
        engine.addSystem(new DespawnSystem());
        engine.addSystem(new DamageSystem());
        engine.addSystem(new ScoreSystem());
        //Should be added last to support CollisionListeners
        engine.addSystem(new CollisionSystem(world));
    }

    @Override
    public void render (float delta) {
        engine.update(delta);
    }

    @Override
    public void dispose () {
        for(EntitySystem system : engine.getSystems()){
            if(system instanceof Disposable){
                ((Disposable)system).dispose();
            }
        }
        world.dispose();

    }

}