package com.jchappelle.sg.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.*;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.jchappelle.sg.*;
import com.jchappelle.sg.components.InputProcessorComponent;
import com.jchappelle.sg.entities.Prefab;
import com.jchappelle.sg.systems.audio.MusicSystem;
import com.jchappelle.sg.systems.audio.SoundSystem;
import com.jchappelle.sg.systems.level.LevelComponent;
import com.jchappelle.sg.systems.damage.DamageSystem;
import com.jchappelle.sg.systems.AsteroidSystem;
import com.jchappelle.sg.systems.level.LevelSystem;
import com.jchappelle.sg.systems.physics.CollisionSystem;
import com.jchappelle.sg.systems.physics.PhysicsSystem;
import com.jchappelle.sg.systems.physics.WorldComponent;
import com.jchappelle.sg.systems.gun.GunSystem;
import com.jchappelle.sg.systems.player.PlayerSystem;
import com.jchappelle.sg.systems.score.ScoreSystem;
import com.jchappelle.sg.systems.render.PhysicsRenderSystem;
import com.jchappelle.sg.systems.render.RenderSystem;
import com.jchappelle.sg.systems.DespawnSystem;
import com.jchappelle.sg.systems.status.StatusEffectSystem;

class GameScreen extends ScreenAdapter {

    private World world;
    private Engine engine;
    private GameManager gameManager;

    GameScreen(GameManager gameManager){
        this.gameManager = gameManager;

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

        engine.addSystem(new MusicSystem(gameManager));
        engine.addSystem(new SoundSystem(gameManager));
        engine.addSystem(new RenderSystem(gameManager));
        engine.addSystem(new PhysicsSystem(world));
        engine.addSystem(new PhysicsRenderSystem(world));
        engine.addSystem(new PlayerSystem(gameManager));
        engine.addSystem(new GunSystem(gameManager));
        engine.addSystem(new AsteroidSystem(gameManager));
        engine.addSystem(new DespawnSystem(gameManager));
        engine.addSystem(new DamageSystem());
        engine.addSystem(new ScoreSystem(gameManager));
        engine.addSystem(new LevelSystem());
        engine.addSystem(new StatusEffectSystem());
        //Should be added last to support CollisionListeners
        engine.addSystem(new CollisionSystem(world));

        Entity player = gameManager.getEntityFactory().make(Prefab.PLAYER_SHIP);
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
