package gameplay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameLoop {
    List<Player> d_playersList = new ArrayList<>();

    List<Country> d_countryList = new ArrayList<>();

    public void startup(){
        /*
        TODO:
         Take input from player
         2 types:
         "gameplayer" - adds player (has args refer the grading sheet)
         "assigncountries" - adds countries randomly to all players and ends startup
          Use InputOutput class to take the input and continue further
         */
    }

    public void looper(){
        for(Player player : d_playersList){
            //TODO: Calculate and assign each player their appropriate reinforcements according to the rules
        }

        while (){ //TODO: Loop till all players have depleted their reinforcements
            for(Player player : d_playersList){
                player.issue_order();
            }
        }

        while (){ //TODO: Loop till all players have depleted their orders
            for(Player player : d_playersList){
            Order pendingOrder = player.next_order();
            pendingOrder.execute();
            }
        }
    }

}
