package com.States;

import com.gameplay.GameEngine;
import com.gameplay.Parsing;
import com.gameplay.Tournament;

/**
 * The Tournament mode phase.
 */
public class TournamentMode implements Phase {
    /**
     * Instantiates a new Tournament mode.
     */
    public TournamentMode() {
        System.out.println("""
                -----------------------------------------------------------------------
                                                TOURNAMENT MODE
                -----------------------------------------------------------------------
                Commands:
                
                tournament
                menu
                -----------------------------------------------------------------------
                """
        );
    }

    @Override
    public String currentPhase() {
        return "TournamentMode";
    }

    public void startTournament(Parsing p_parsing) {
        GameEngine l_gameEngine = new GameEngine();
        new Tournament(l_gameEngine).start(p_parsing);
    }
}
