package com.DCB;

import java.util.Scanner;

public class ScriptReader {
    private final Scanner scanner;

    public ScriptReader(Scanner scanner) {
        this.scanner = scanner;
        scanner.useDelimiter("");
    }

    private char previewChar = ' ';
    public char getNextChar() {
        if(previewChar != ' ') {
            char output = previewChar;
            previewChar = ' ';
            return output;
        }
        return scanner.next().charAt(0);
    }

    public char previewNextChar() {
        previewChar = scanner.next().charAt(0);
        return previewChar;
    }

    public boolean hasNextChar() {
        return scanner.hasNext();
    }
}
