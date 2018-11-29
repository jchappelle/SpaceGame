package com.jchappelle.sg.crashhandler;

import com.jchappelle.sg.GameManager;
import com.jchappelle.sg.screens.ScreenId;

class SimpleCrashHandler implements CrashHandler {

    private GameManager gameManager;

    public SimpleCrashHandler(GameManager gameManager){
        this.gameManager = gameManager;
    }

    public void handleCrash(Exception e){
        e.printStackTrace();

        gameManager.changeScreen(ScreenId.MAIN_MENU);
    }

}
