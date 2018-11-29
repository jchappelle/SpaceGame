package com.jchappelle.sg.preferences;

public final class AppPreferencesFactory {

    public static AppPreferences make(){
        return new DefaultAppPreferences();
    }

    private AppPreferencesFactory(){}
}
