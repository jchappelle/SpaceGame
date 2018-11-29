package com.jchappelle.sg;

import com.jchappelle.sg.preferences.AppPreferences;
import com.jchappelle.sg.screens.ScreenId;

public interface GameManager {

    void changeScreen(ScreenId screen);

    AppPreferences getPreferences();

}
