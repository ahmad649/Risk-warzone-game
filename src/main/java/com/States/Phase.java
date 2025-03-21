package com.States;

import com.gameplay.Command;
import com.gameplay.GameEngine;

public interface Phase {
    void addGamePlayer(GameEngine engine, Command l_command);
}
