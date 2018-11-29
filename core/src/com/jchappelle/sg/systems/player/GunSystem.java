package com.jchappelle.sg.systems.player;

import com.badlogic.ashley.core.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.World;
import com.jchappelle.sg.Entities;
import com.jchappelle.sg.components.TransformComponent;
import com.jchappelle.sg.systems.physics.WorldComponent;
import com.jchappelle.sg.systems.render.SpriteComponent;

public class GunSystem extends EntitySystem {

    private Entity player;
    private Sprite playerSprite;
    private World world;

    private Engine engine;
    private GunComponent gun;

    public void addedToEngine(Engine engine) {
        this.engine = engine;
        player = Entities.get().getPlayer();
        gun = GunComponent.get(player);

        Entity game = Entities.get().getGame();
        world = WorldComponent.get(game).world;

        playerSprite = SpriteComponent.get(player).sprite;
    }

    public void update(float deltaTime) {
        boolean firing = Gdx.input.isKeyPressed(Input.Keys.SPACE);
        if(firing && canFire(gun)){
            fire();
            gun.lastBulletShot = System.currentTimeMillis();
        }
    }

    public void fire(){
        TransformComponent tc = TransformComponent.get(player);
        float x = tc.x + tc.width/2 - 4;
        float y = tc.y + tc.height/2 + 16;
        engine.addEntity(Entities.get().newBullet(x, y));
    }

    public boolean canFire(GunComponent gc){
        return (System.currentTimeMillis() - gc.lastBulletShot) > gc.coolDownMillis;
    }
}
