package org.devi. interfaces;

public enum ExitCode {
    EXIT_SUCCESS,
    EXIT_FAILURE;

    public int getCode() {
        return switch (this) {
            case EXIT_SUCCESS -> 0;
            case EXIT_FAILURE -> 1;
        };
    }

}