package org.devi.interfaces;

public class Messenger {
    public final String SYSTEM_NAME = "system";
    public final int TOTAL_WIDTH = 40;

    public void print(String message) {
        System.out.print(message);
    }

    public void println(String message) {
        System.out.println(message);
    }

    public void newLine() {
        System.out.println();
    }

    public void systemMessage(String message) {
        println("[" + SYSTEM_NAME + "] " + message);
    }

    public void printHeader(String title) {
        newLine();
        if (title.length() > TOTAL_WIDTH) {
            println(title);
            return;
        }
        final int dashLength = getDashLength(title);
        final String dashesLeft = "·".repeat(dashLength / 2);
        final String dashesRight = "·".repeat(dashLength / 2);
        println(String.format("%s %s %s", dashesLeft, title, dashesRight));
    }

    public void printDashSeparator() {
        println(".".repeat(TOTAL_WIDTH + 2));
    }

    private int getDashLength(String title) {
        return TOTAL_WIDTH - title.length();
    }

    public String generateSpaces(int length) {
        final int count = length > TOTAL_WIDTH ? 1 : TOTAL_WIDTH - (length + 3);
        return " ".repeat(count);
    }
}
