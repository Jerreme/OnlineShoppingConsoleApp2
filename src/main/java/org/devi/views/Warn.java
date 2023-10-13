package org.devi.views;

import org.devi.interfaces.ExitCode;

import java.sql.SQLNonTransientConnectionException;
import java.sql.SQLTimeoutException;
import java.sql.SQLTransientConnectionException;

public class Warn {

    public static void invalidInput() {
        System.out.println("[system] Please enter a valid input.");
    }

    public static void systemMessage(String message) {
        System.out.println("[system] " + message);
    }

    public static void debugMessage(String message) {
        System.err.println("[debug] " + message);
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public static void databaseError(Exception e) {
        if (e instanceof SQLTimeoutException || (e.getMessage() != null && e.getMessage().contains("timed out"))) {
            System.err.println("[debug] Please check your internet connection!");
            return;
        }
        System.err.println("[debug] " + e.getClass());
        e.printStackTrace();
    }

    public static void debugMessageAndExit(String message, ExitCode statusCode) {
        debugMessage(message);
        debugMessage("Exiting with status code " + statusCode);
        System.exit(statusCode.getCode());
    }
}

