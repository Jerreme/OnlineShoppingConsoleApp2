package org.devi. controllers;

import org.devi.interfaces.Credential;
import org.devi.models.Admin;
import org.devi.models.User;
import org.devi.views.LoginPageView;

import java.util.Scanner;

public class LoginController {

    private static final int MAX_LOGIN_ATTEMPT = 3;
    private static int loginAttemptCount = 0;
    private final LoginPageView view;
    private final CredentialManager credentialManager = new CredentialManager();

    public LoginController(LoginPageView view) {
        this.view = view;
    }

    private boolean isCanLogin() {
        if (loginAttemptCount == 0) return true;
        if (loginAttemptCount >= MAX_LOGIN_ATTEMPT) {
            loginAttemptCount = 0;
            view.warnMaxLoginAttempt();
            return false;
        } else {
            view.showLoginAttemptCount(MAX_LOGIN_ATTEMPT - loginAttemptCount);
            return true;
        }
    }

    public Credential promptLogin() {
        // Check login attempts first
        if (!isCanLogin()) return null;
        Scanner scanner = new Scanner(System.in);

        view.showLogin();
        view.showUsername();
        final String username = scanner.nextLine().trim().toLowerCase();
        view.showPassword();
        final String password = scanner.nextLine().trim().toLowerCase();

        loginAttemptCount += 1;
        if (username.contains("admin-")) return new Admin(username, password);
        else return new User(username, password, User.DEFAULT_MONEY);
    }

    /**
     * @param loginCredential The login credential to be submitted
     * @return The user credential if the login credential is valid, null otherwise
     */
    public Credential submitLoginCredential(Credential loginCredential) {
        final Credential userCredential;
        if (loginCredential instanceof Admin) {
            userCredential = credentialManager.tryLoginAsAdmin(loginCredential);
        } else {
            userCredential = credentialManager.tryLoginAsUser(loginCredential);
        }

        if (userCredential == null) {
            view.showLoginFailed();
        } else {
            view.showLoginSuccess();
            loginAttemptCount = 0;
        }
        return userCredential;
    }
}
