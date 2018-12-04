package com.jchappelle.sg.systems.gun;

import com.badlogic.ashley.core.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.jchappelle.sg.GameManager;
import com.jchappelle.sg.components.TransformComponent;
import com.jchappelle.sg.systems.physics.BodyComponent;

public class GunSystem extends EntitySystem {

    private Engine engine;

    private GameManager gameManager;

    public GunSystem(GameManager gameManager){
        this.gameManager = gameManager;
    }

    public void addedToEngine(Engine engine) {
        this.engine = engine;
    }

    public void update(float deltaTime) {
        boolean firing = Gdx.input.isKeyPressed(Input.Keys.SPACE);
        if(firing){
            Entity player = gameManager.getPlayer();
            if(player != null){
                GunComponent gun = GunComponent.get(player);
                if(canFire(gun)){
                    fire(player, gun);
                }
            }
        }
    }

    private void fire(Entity player, GunComponent gun){
        TransformComponent tc = TransformComponent.get(player);
        float x = tc.x + tc.width/2 - 4;
        float y = tc.y + tc.height/2 + 16;

        MultishotComponent ms = MultishotComponent.get(player);
        if(ms != null){
            fireMultishot(gun.prefabId, ms.bulletCount, x, y, gun.speed);
        }
        else{
            fireBullet(gun.prefabId, x, y, velocity(90, gun.speed));
        }
        gun.lastBulletShot = System.currentTimeMillis();
    }

    private void fireMultishot(String prefabId, int bulletCount, float x, float y, float speed){
        fireBullet(prefabId, x, y, velocity(90, speed));
        float angleInterval = 90f/((bulletCount-1));

        float angleAccumulator = 90 + angleInterval;
        for(int i = 0; i < (bulletCount - 1)/2; i++, angleAccumulator+=angleInterval){
            fireBullet(prefabId, x, y, velocity(angleAccumulator, speed));
        }
        angleAccumulator = 90 - angleInterval;
        for(int i = 0; i < (bulletCount - 1)/2; i++, angleAccumulator-=angleInterval){
            fireBullet(prefabId, x, y, velocity(angleAccumulator, speed));
        }
    }

    private void fireBullet(String prefabId, float x, float y, Vector2 initialForce){
        Entity bullet = gameManager.getEntityFactory().make(prefabId, new TransformComponent(x, y));
        if(initialForce != null){
            BodyComponent bc = BodyComponent.get(bullet);
            if(bc != null){
                bc.initialForce = initialForce;
            }
        }
        engine.addEntity(bullet);
    }

    private Vector2 velocity(float angle, float bulletSpeed){
        return new Vector2(MathUtils.cos(angle * MathUtils.degRad) * bulletSpeed, MathUtils.sin(angle * MathUtils.degRad) * bulletSpeed);
    }

    private boolean canFire(GunComponent gc){
        return (System.currentTimeMillis() - gc.lastBulletShot) > gc.coolDownMillis;
    }
}
