package com.jchappelle.sg.screens;

import com.badlogic.gdx.Screen;
import com.jchappelle.sg.GameManager;

public final class ScreenFactory {

    public static Screen makeScreen(ScreenId screenId, GameManager gameManager){
        switch(screenId) {
            case MAIN_MENU:
                return new MainMenuScreen(gameManager);
            case GAME:
                return new GameScreen(gameManager);
            case PREFERENCES:
                return new PreferencesScreen(gameManager);
        }
        throw new IllegalArgumentException("Unsupported ScreenId of " + screenId);
    }

    private ScreenFactory(){}
}
