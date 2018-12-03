package com.jchappelle.sg.systems.player;

import com.badlogic.ashley.core.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.World;
import com.jchappelle.sg.Entities;
import com.jchappelle.sg.GameManager;
import com.jchappelle.sg.components.SpawnSourceComponent;
import com.jchappelle.sg.components.TransformComponent;
import com.jchappelle.sg.entities.Prefab;
import com.jchappelle.sg.systems.physics.WorldComponent;

public class GunSystem extends EntitySystem {

    private World world;

    private Engine engine;

    private GameManager gameManager;

    public GunSystem(GameManager gameManager){
        this.gameManager = gameManager;
    }

    public void addedToEngine(Engine engine) {
        this.engine = engine;

        Entity game = Entities.get().getGame();
        world = WorldComponent.get(game).world;
    }

    public void update(float deltaTime) {
        boolean firing = Gdx.input.isKeyPressed(Input.Keys.SPACE);
        if(firing){
            Entity player = gameManager.getPlayer();
            if(player != null){
                GunComponent gun = GunComponent.get(player);
                if(canFire(gun)){
                    fire(player);
                    gun.lastBulletShot = System.currentTimeMillis();
                }
            }
        }
    }

    private void fire(Entity player){
        TransformComponent tc = TransformComponent.get(player);
        float x = tc.x + tc.width/2 - 4;
        float y = tc.y + tc.height/2 + 16;

        Entity bullet = gameManager.getEntityFactory().make(Prefab.BULLET, new TransformComponent(x, y));
        engine.addEntity(bullet);
    }

    private boolean canFire(GunComponent gc){
        return (System.currentTimeMillis() - gc.lastBulletShot) > gc.coolDownMillis;
    }
}
