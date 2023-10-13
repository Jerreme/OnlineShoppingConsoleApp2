package org.devi. models;

import org.devi.interfaces.Credential;
import org.devi.interfaces.UserBalanceException;

public record User(String username, String password, int balance) implements Credential {
    public static final int DEFAULT_MONEY = 1000;
    public static final int MINIMUM_CASH_IN_AMOUNT = 100;
    public static final int MAXIMUM_CASH_IN_AMOUNT = 3000;

    public User spend(int amount) throws UserBalanceException {
        if (amount > balance) throw new UserBalanceException("Your E-Wallet: ₱" + balance);
        return new User(username, password, balance - amount);
    }

    public User deposit(int amount) throws UserBalanceException {
        if (amount < MINIMUM_CASH_IN_AMOUNT || amount > MAXIMUM_CASH_IN_AMOUNT) {
            throw new UserBalanceException("Invalid Amount");
        }
        return new User(username, password, balance + amount);
    }

    public int getWalletBalance() {
        return balance;
    }

    public String toString() {
        return String.format("username: %s, password: %s, balance: %d",username, password, balance );
    }
}
