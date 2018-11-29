package com.jchappelle.sg.crashhandler;

import com.jchappelle.sg.GameManager;

public final class CrashHandlerFactory {

    public static CrashHandler make(GameManager gameManager){
        return new SimpleCrashHandler(gameManager);
    }

    private CrashHandlerFactory(){}
}
