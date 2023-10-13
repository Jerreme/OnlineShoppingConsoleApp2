package org.devi. controllers;

import org.devi.interfaces.UserBalanceException;
import org.devi.models.User;
import org.devi.views.ProfilePageView;
import org.devi.views.Warn;

import java.util.Scanner;

public class ProfilePageController {
    private final ProfilePageView view;

    public ProfilePageController(ProfilePageView view) {
        this.view = view;
    }

    public void cashIn() {
        int amount = askForAmount();
        try {
            final User newUser = CredentialManager.getLoggedInUser().deposit(amount);
            CredentialManager.updateUser(newUser);
            view.showSuccessDepositMessage(amount);
        } catch (UserBalanceException e) {
            view.showInvalidAmountMessage(User.MINIMUM_CASH_IN_AMOUNT, User.MAXIMUM_CASH_IN_AMOUNT);
        }
        Navigator.rebuildActiveRoute();
    }

    private int askForAmount() {
        view.showCashInPage(User.MINIMUM_CASH_IN_AMOUNT, User.MAXIMUM_CASH_IN_AMOUNT);
        Scanner scanner = new Scanner(System.in);
        try {
            return scanner.nextInt();
        } catch (Exception e) {
            Warn.invalidInput();
            return askForAmount();
        }
    }
}
