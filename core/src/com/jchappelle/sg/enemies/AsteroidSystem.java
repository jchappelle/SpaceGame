package com.jchappelle.sg.enemies;

import com.badlogic.ashley.core.*;
import com.badlogic.gdx.Gdx;
import com.jchappelle.sg.Entities;

import java.util.Random;

public class AsteroidSystem extends EntitySystem {

    private Engine engine;

    private Random rand = new Random();

    public void addedToEngine(Engine engine){
        this.engine = engine;
    }

    public void update(float deltaTime){
        if(rand.nextFloat() < 0.01f){
            spawnAsteroid();
        }
    }

    private void spawnAsteroid(){
        int x = rand.nextInt(Gdx.graphics.getWidth());
        int y = Gdx.graphics.getHeight();
        engine.addEntity(Entities.get().newAsteroid(x, y));
    }
}
