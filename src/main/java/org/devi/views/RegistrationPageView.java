package org.devi. views;

import org.devi.interfaces.Messenger;

public class RegistrationPageView extends Messenger {

    public void showRegistration() {
        printHeader("Register");
    }

    public void showUsername() {
        print("Enter username: ");
    }

    public void showPassword() {
        print("Enter password: ");
    }

    public void showConfirmPassword() {
        print("Confirm password: ");
    }

    public void showRegistrationSuccess() {
        systemMessage("Registered successfully.");
    }

    public void showRegistrationFailed() {
        systemMessage("Registration failed. Passwords does not match.");
    }

    public void showRegistrationAttemptCount(int remainingAttempt) {
        systemMessage("You have " + remainingAttempt + " registration attempt(s) left.");
    }

    public void warnMaxRegistrationAttempt() {
        systemMessage("You have reached the maximum registration attempt.");
    }

    public void warnDuplicateRegistration() {
        systemMessage("Username already exists.");
    }
}
