package org.devi.controllers;

import org.devi.database.UserDb;
import org.devi.interfaces.Credential;
import org.devi.models.Admin;
import org.devi.models.User;
import org.devi.views.Warn;

import java.util.ArrayList;

public class CredentialManager {
    private static User loggedUser = null;
    private static Admin loggedAdmin = null;

    public void registerUser(User user) {
        if (!isCredentialValidd(user)) return;
        final UserDb userDb = new UserDb();
        userDb.addUser(user);
    }

    public ArrayList<User> getAllUsers() {
        final UserDb userDb = new UserDb();
        return userDb.getAllUsers();
    }

    public void registerAdmin(Admin admin) {
        if (!isCredentialValidd(admin)) return;
        final UserDb userDb = new UserDb();
        userDb.addAdmin(admin);
    }

    public ArrayList<Admin> getAllAdmins() {
        final UserDb userDb = new UserDb();
        return userDb.getAllAdmins();
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean isCredentialValidd(Credential credential) {
        if (credential.username() == null || credential.password() == null) {
            Warn.debugMessage("Username or password is null!");
            return false;
        }
        if (credential.username().isEmpty() || credential.password().isEmpty()) {
            Warn.debugMessage("Username or password is empty!");
            return false;
        }
        return true;
    }

    public User tryLoginAsUser(Credential credential) {
        final UserDb userDb = new UserDb();
        final User user = userDb.getUser(credential);
        CredentialManager.loggedUser = user;
        return user;
    }

    public Admin tryLoginAsAdmin(Credential credential) {
        final UserDb userDb = new UserDb();
        final Admin admin = userDb.getAdmin(credential);
        CredentialManager.loggedAdmin = admin;
        return admin;
    }


    public boolean isUSerAlreadyExist(String username) {
        final UserDb userDb = new UserDb();
        return userDb.isUserAlreadyExist(username);
    }

    public static void refreshLoggedUser() {
        final UserDb userDb = new UserDb();
        CredentialManager.loggedUser = userDb.getUser(CredentialManager.loggedUser);
    }

    public static User getLoggedInUser() {
        return CredentialManager.loggedUser;
    }

    public static Admin getLoggedInAdmin() {
        return CredentialManager.loggedAdmin;
    }

    public static void updateUser(User user) {
        CredentialManager.loggedUser = user;
        // Update user in database
        final UserDb userDb = new UserDb();
        userDb.updateUserBalance(user);
    }

    public static void logout() {
        CredentialManager.loggedUser = null;
        CredentialManager.loggedAdmin = null;
    }
}
