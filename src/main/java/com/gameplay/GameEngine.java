package com.gameplay;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.States.Menu;
import com.States.Phase;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.logs.LogEntryBuffer;
import com.logs.LogFileWriter;
import com.model.Country;

/**
 * GameEngine class that works as the controller for the game flow
 * Manages the game stages and has the list for Player and Country objects:
 * d_playerList: list of player objects
 * d_countryList: list of country objects
 */
public class GameEngine {
    /**
     * A list of players.
     */
    public List<Player> d_playersList = new ArrayList<>();
    /**
     * A list of countries.
     */
    public List<Country> d_countryList = new ArrayList<>();

    /**
     * The game phase.
     */
    public Phase d_phase;

    private final Player d_neutralPlayer;
    /**
     * The log buffer that stores all log entries for the game.
     * It is used to keep track of all actions and events that occur during the game.
     */
    public LogEntryBuffer d_logbuffer = new LogEntryBuffer();


    /**
     * Instantiates a new Game engine.
     */
    public GameEngine(){
        d_phase = new Menu(this);
        d_logbuffer.addObserver(new LogFileWriter("logs/logs-"+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss")) + ".txt"));

        this.d_neutralPlayer = new Player("Neutral");
    }

    /**
     * Gets players list.
     * @return the players list
     */
    public List<Player> getPlayersList() {
        return d_playersList;
    }

    /**
     * Get neutral player.
     *
     * @return the neutral player
     */
    public Player getneutralPlayer() {
        return this.d_neutralPlayer;
    }

    /**
     * Sets country list.
     *
     * @param countryList the country list
     */
    public void setCountryList(List<Country> countryList) {
        this.d_countryList = countryList;
    }

    // Game starter

    /**
     * startup() method in charge of initiating the game taking no parameters
     * Manages player creation and controls the first stage of the game managing the users input through the InputOutput class methods
     * If the command given by the user is valid, Command class methods are called to determine either if the command adds or removes players
     * On the other hand, if the command corresponds to assigning countries, the process is called as well as the loop method running the main stage of the game
     */
    public void gameLoop() {
        while (true) {
            Parsing l_parsing = null;
            while (l_parsing == null) {
                if (d_phase.currentPhase().equals("IssueOrder") && d_phase.checkIfNonHuman()){
                    d_phase.processIfNonHuman();
                    continue;
                }
                l_parsing = InputOutput.get_user_command();
            }
            if (l_parsing.d_commandType.equals("startgame")){
                d_phase.startGame();
            } else if (l_parsing.d_commandType.equals("starttournament")){
                d_phase.startTournament(l_parsing);
            } else if ((l_parsing.d_commandType.equals("editor"))){
                d_phase.editor();
            } else if(l_parsing.d_commandType.equals("gameplayer")) {
                d_phase.addGamePlayer(l_parsing);
            } else if (l_parsing.d_commandType.equals("loadmap")) {
                d_phase.loadMap(l_parsing);
            } else if (l_parsing.d_commandType.equals("showmap")) {
                d_phase.displayMap();
            } else if (l_parsing.d_commandType.equals("endturn")) {
                d_phase.endTurn();
            } else if (l_parsing.d_commandType.equals("assigncountries")) {
                d_phase.assignCountries();
            } else if (checkIssuable(l_parsing)) {
                d_phase.createOrder(l_parsing);
            } else if (l_parsing.d_commandType.equals("quit")) {
                return;
            }else if (l_parsing.d_commandType.equals("validatemap")){
                d_phase.validateMap();
            }else if (l_parsing.d_commandType.equals("editcountry")){
                d_phase.editCountry(l_parsing);
            }else if (l_parsing.d_commandType.equals("editcontinent")){
                d_phase.editContinent(l_parsing);
            }else if (l_parsing.d_commandType.equals("editneighbor")){
                d_phase.editNeighbor(l_parsing);
            }else if (l_parsing.d_commandType.equals("editmap")){
                d_phase.editMap(l_parsing);
            }else if (l_parsing.d_commandType.equals("savemap")){
                d_phase.saveMap(l_parsing);
            }else if (l_parsing.d_commandType.equals("menu")){
                d_phase.returnToMenu(this);
            }else if (l_parsing.d_commandType.equals("tournament")){
                d_phase.startTournament(l_parsing);
            }else if (l_parsing.d_commandType.equals("savegame")){
                this.saveGameState(l_parsing);
            }else if (l_parsing.d_commandType.equals("loadgame")){
                this.loadGameState(l_parsing);
            }else {
                System.out.println("Invalid command. Try again.");
            }
        }
    }

    /**
     * Check if the command is issuable.
     *
     * @param l_parsing input parsed for usage
     * @return boolean
     */
    public boolean checkIssuable(Parsing l_parsing) {
        ArrayList<String> possibleOrders = new ArrayList<>(List.of("deploy","advance","bomb","negotiate","airlift","blockade"));
        return possibleOrders.contains(l_parsing.d_commandType.toLowerCase());
    }

    /**
     * Saves the current game state to a JSON file. The filename is determined
     * by the first argument in the provided Parsing object's argument array.
     * The save file is created in the "src/main/java/com/GameFiles" directory.
     *
     * @param l_parsing a Parsing object that holds the command arguments;
     *                  the first argument (obtained via d_argArr.getFirst()) should be the filename.
     */
    public void saveGameState(Parsing l_parsing) {
        String filePath = "src/main/java/com/GameFiles/" + l_parsing.d_argArr.getFirst() + ".json";
        GameState gameState = new GameState();
        gameState.setD_players(this.d_playersList);
        gameState.setD_countries(this.d_countryList);
        gameState.setD_currentPhase(this.d_phase);
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), gameState);
            System.out.println("Game saved successfully to " + l_parsing);
        } catch (IOException e) {
            System.err.println("Error saving game: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Loads the game state from a JSON file. The filename is determined by the first
     * argument in the provided Parsing object's argument array. The file is expected
     * to be located in the "src/main/java/com/GameFiles" directory.
     * Once loaded, the players list, countries list, and current phase in the GameEngine
     * are updated with the loaded state.
     *
     * @param l_parsing a Parsing object that holds the command arguments;
     *                  the first argument (obtained via d_argArr.getFirst()) should be the filename.
     */
    public void loadGameState(Parsing l_parsing) {
        String filePath = "src/main/java/com/GameFiles/" + l_parsing.d_argArr.getFirst() + ".json";
        ObjectMapper mapper = new ObjectMapper();
        try {
            GameState loadedState = mapper.readValue(new File(filePath), GameState.class);
            this.d_playersList = loadedState.getD_players();
            this.d_countryList = loadedState.getD_countries();
            this.d_phase = loadedState.getD_currentPhase();
            System.out.println("Game loaded successfully from " + filePath);
        } catch (IOException e) {
            System.err.println("Error loading game: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Clear player list.
     */
    public void clearPlayerList() {
        this.d_playersList.clear();
    }

    /**
     * Clear country list.
     */
    public void clearCountryList() {
        this.d_countryList.clear();
    }
}
