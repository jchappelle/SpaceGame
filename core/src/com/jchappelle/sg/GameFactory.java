package com.jchappelle.sg;

import com.badlogic.gdx.ApplicationListener;

public final class GameFactory {

    public static ApplicationListener makeGame(){
        return new SpaceGame();
    }

    private GameFactory(){}
}
