package com.gameplay;

import java.util.Scanner;

/**
 * MainMenu class containing the visual representation of the command input and mode selection.
 * This is the first element the user sees once they run the program, giving the options of:
 * 1. Map Editor: access the map menu and commands
 * 2. Play Warzone Game: starts the game
 * 3. Exit: exits the menu and finishes the program
 */
public class MainMenu {
    /**
     * Run main menu.
     */
    public void run_main_menu() {
        int l_input = 0;

        do {
            System.out.println("\n***********************");
            System.out.println("* Welcome to Warzone *");
            System.out.println("***********************");

            Scanner l_scanner = new Scanner(System.in);

            System.out.println("\ncom.Main Menu:");
            System.out.println("1. Map Editor");
            System.out.println("2. Play Warzone Game");
            System.out.println("3. Exit\n");
            System.out.println("Enter your choice: ");

            // Check whether user input is an integer or not
            if (l_scanner.hasNextInt()) {
                l_input = l_scanner.nextInt();

                // Perform actions based on user input
                switch (l_input) {
                    // Runs map editor mode
                    case 1:
//                        InputOutput l_inputOutput = new InputOutput();
//                        l_inputOutput.run_map_editor();
                        break;
                    // Starts the Warzone Game
                    case 2:
                        GameEngine gameEngine = new GameEngine();
                        gameEngine.gameLoop();
                        break;
                    // Finishes the program, prompting the user an exit message
                    case 3:
                        System.out.println("\nSuccessfully exit the game.");
                        break;
                }
            } else {
                System.out.println("\nInvalid input. Please try again.\n");
            }
        } while(l_input != 3);  // Keep looping if user doesn't choose exit
    }
}
