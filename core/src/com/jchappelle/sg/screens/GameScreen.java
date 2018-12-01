package com.jchappelle.sg.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.*;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.jchappelle.sg.*;
import com.jchappelle.sg.components.InputProcessorComponent;
import com.jchappelle.sg.systems.MusicSystem;
import com.jchappelle.sg.systems.SoundSystem;
import com.jchappelle.sg.systems.level.LevelComponent;
import com.jchappelle.sg.systems.damage.DamageSystem;
import com.jchappelle.sg.systems.AsteroidSystem;
import com.jchappelle.sg.systems.level.LevelSystem;
import com.jchappelle.sg.systems.physics.CollisionSystem;
import com.jchappelle.sg.systems.physics.PhysicsSystem;
import com.jchappelle.sg.systems.physics.WorldComponent;
import com.jchappelle.sg.systems.player.GunSystem;
import com.jchappelle.sg.systems.player.PlayerSystem;
import com.jchappelle.sg.systems.player.ScoreSystem;
import com.jchappelle.sg.systems.render.PhysicsRenderSystem;
import com.jchappelle.sg.systems.render.RenderSystem;
import com.jchappelle.sg.systems.DespawnSystem;

class GameScreen extends ScreenAdapter {

    private World world;
    private Engine engine;
    private GameManager gameManager;

    GameScreen(GameManager gameManager){
        this.gameManager = gameManager;
    }

    @Override
    public void show(){
        engine = new Engine();
        Entity gameEntity = new Entity();
        WorldComponent worldComponent = new WorldComponent();
        world = worldComponent.world;
        gameEntity.add(worldComponent);
        gameEntity.add(new LevelComponent());
        InputProcessor inputProcessor = new InputMultiplexer();
        gameEntity.add(new InputProcessorComponent(inputProcessor));
        Gdx.input.setInputProcessor(inputProcessor);
        engine.addEntity(gameEntity);

        Entities.init(world, engine);

        engine.addSystem(new MusicSystem());
        engine.addSystem(new SoundSystem(gameManager));
        engine.addSystem(new RenderSystem(gameManager));
        engine.addSystem(new PhysicsSystem(world));
        engine.addSystem(new PhysicsRenderSystem(world));
        engine.addSystem(new PlayerSystem());
        engine.addSystem(new GunSystem());
        engine.addSystem(new AsteroidSystem(gameManager));
        engine.addSystem(new DespawnSystem());
        engine.addSystem(new DamageSystem());
        engine.addSystem(new ScoreSystem());
        engine.addSystem(new LevelSystem());
        //Should be added last to support CollisionListeners
        engine.addSystem(new CollisionSystem(world));

        Entity player = Entities.get().newShip();
        gameManager.setPlayer(player);
        engine.addEntity(player);
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
