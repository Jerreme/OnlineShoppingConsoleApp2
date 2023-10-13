package org.devi. views;

import org.devi.interfaces.Messenger;

public class HomePageView extends Messenger {
    public void showHomePageOptions() {
        printHeader("Home");
        println("[1] Buy Products");
        println("[2] View Purchase Logs");
        println("[3] Profile");
        println("[0] Logout");
    }

    public void welcomeUser(String username) {
        systemMessage("Welcome " + username + "!");
    }

    public void showLogoutMessage() {
        systemMessage("You have been logged out.");
    }
}
