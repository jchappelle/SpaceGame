package com.jchappelle.sg;

import com.jchappelle.sg.AppPreferences;
import com.jchappelle.sg.screens.ScreenId;

public interface GameManager {

    void changeScreen(ScreenId screen);

    AppPreferences getPreferences();

}
