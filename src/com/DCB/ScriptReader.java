package com.DCB;

import java.util.Scanner;

public class ScriptReader {
    private final Scanner scanner;

    public ScriptReader(Scanner scanner) {
        this.scanner = scanner;
        scanner.useDelimiter("");
    }

    public char getNextChar() {
        return scanner.next().charAt(0);
    }


    public boolean hasNextChar() {
        return scanner.hasNext();
    }
}
