package com.jchappelle.sg.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.gdx.Gdx;
import com.jchappelle.sg.Entities;
import com.jchappelle.sg.GameManager;
import com.jchappelle.sg.entities.EntityUtils;
import com.jchappelle.sg.entities.Prefab;
import com.jchappelle.sg.systems.level.LevelComponent;

import java.util.Random;

public class AsteroidSystem extends EntitySystem implements EntityListener {

    private Engine engine;

    private Random rand = new Random();
    private LevelComponent lc;

    private GameManager gameManager;
    public AsteroidSystem(GameManager gameManager){
        this.gameManager = gameManager;
    }
    public void addedToEngine(Engine engine){
        this.engine = engine;
        this.engine.addEntityListener(this);
    }

    public void update(float deltaTime){
        float probability = (0.01f + (getLevel() * 4 / 100));
        if(rand.nextFloat() < probability){
            spawnAsteroid();
        }
    }

    private int getLevel(){
        if(lc == null){
            lc = LevelComponent.get(Entities.get().getGame());
        }
        return lc.level;
    }

    private void spawnAsteroid(){
        int x = rand.nextInt(Gdx.graphics.getWidth());
        int y = Gdx.graphics.getHeight();
        Entity entity = gameManager.getEntityFactory().make(Prefab.ASTEROID);
        EntityUtils.setPosition(entity, x, y);
        engine.addEntity(entity);
    }

    @Override
    public void entityAdded(Entity entity) {

    }

    @Override
    public void entityRemoved(Entity entity) {

    }
}
