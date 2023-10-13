package org.devi. views;


import org.devi.interfaces.Messenger;
import org.devi.models.User;

public class ProfilePageView extends Messenger {

    public void showProfilePage(User user) {
        final String password = "*".repeat(user.password().length());
        printHeader("User Profile");
        println("username: " + user.username());
        println("password: " + password);
        newLine();
        println("E-Wallet");
        println("balance: ₱" + user.balance());
    }

    public void showProfileOptions() {
        println("[1] Cash In");
        println("[0] Back");
    }

    public void showCashInPage(int minimumCashInAmount, int maximumCashInAmount) {
        printHeader("Cash In");
        println("Enter amount [₱" + minimumCashInAmount + " - ₱" + maximumCashInAmount + "]");
        print(">> ₱");
    }

    public void showInvalidAmountMessage(int minimumCashInAmount, int maximumCashInAmount) {
        systemMessage("Please enter an amount between ₱" + minimumCashInAmount + " and ₱" + maximumCashInAmount + ".");
    }

    public void showSuccessDepositMessage(int amount) {
        systemMessage(String.format("%d has been deposited to your account.", amount));
    }
}
