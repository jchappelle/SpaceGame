package com.jchappelle.sg.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.gdx.Gdx;
import com.jchappelle.sg.Entities;
import com.jchappelle.sg.systems.level.LevelComponent;

import java.util.Random;

public class AsteroidSystem extends EntitySystem {

    private Engine engine;

    private Random rand = new Random();
    private LevelComponent lc;

    public void addedToEngine(Engine engine){
        this.engine = engine;
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
        engine.addEntity(Entities.get().newAsteroid(x, y));
    }
}
