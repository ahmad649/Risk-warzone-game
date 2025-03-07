package com;

import com.gameplay.MainMenu;

/**
 * Main class to run the application
 */
public class Main {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        MainMenu l_mainMenu = new MainMenu();

        l_mainMenu.run_main_menu();
    }
}
