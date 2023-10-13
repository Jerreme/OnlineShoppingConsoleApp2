package org.devi. views;

import org.devi.interfaces.Messenger;

public class IntroPageView extends Messenger {
    public void showAuthScreen() {
        println("\nWelcome to the Online Shopping Console App!");
        println("[1] Login");
        println("[2] Register");
        println("[0] Exit");
    }

    public void showOutro() {
        printDashSeparator();
        systemMessage("Exiting...");
    }


}
