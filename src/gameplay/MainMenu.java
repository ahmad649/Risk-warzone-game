package gameplay;

import java.util.Scanner;

public class MainMenu {
    public void run_main_menu() {
        int l_input = 0;

        do {
            System.out.println("\n***********************");
            System.out.println("* Welcome to Warzone *");
            System.out.println("***********************");

            Scanner l_scanner = new Scanner(System.in);

            System.out.println("\nMain Menu:");
            System.out.println("1. Map Editor");
            System.out.println("2. Play Warzone Game");
            System.out.println("3. Exit\n");
            System.out.println("Enter your choice: ");

            // Check whether user input is an integer or not
            if (l_scanner.hasNextInt()) {
                l_input = l_scanner.nextInt();

                // perform actions based on user input
                switch (l_input) {
                    case 1:
                        InputOutput.run_map_editor();
                        break;
                    case 2:
                        GameLoop gameLoop = new GameLoop();
                        gameLoop.startup();
                        break;
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
