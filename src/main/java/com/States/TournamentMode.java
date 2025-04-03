package com.States;

import com.gameplay.Parsing;
import com.strategy.Tournament;

public class TournamentMode implements Phase {
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
        new Tournament().start(p_parsing);
    }
}
