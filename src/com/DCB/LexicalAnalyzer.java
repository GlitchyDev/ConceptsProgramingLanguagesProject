package com.DCB;

import com.DCB.HelperStrucs.Identifier;
import com.DCB.HelperStrucs.KeyWord;
import com.DCB.HelperStrucs.Value;

import java.util.ArrayList;

public class LexicalAnalyzer {
    // Every token we have currently analyzed, can be of object type KeyWord, Value, or Identifier
    private ArrayList<Object> analyzedScript = new ArrayList<>();
    // Keeping track of our Identifiers, aka Variables as they are declared so we can reuse their objects across each instance
    private ArrayList<Identifier> identifiers = new ArrayList<>();


    public LexicalAnalyzer(ScriptReader scriptReader) {
        // We keep track of every failed character we have pulled out till we get a success or run out of characters
        String currentString = "";
        // Keep going on the loop till we run out of document
        while(scriptReader.hasNextChar()) {
            char c = scriptReader.getNextChar();
            // We ignore spaces like a bad ass :O
            if(c != ' ') {
                // Add this character to our current collection of characters
                currentString += scriptReader.getNextChar();
                // This if statement will check and see if it can create a valid VariableType, Operator, Function Token, or Value
                // From the Given input, if it passes, it resets the currentString and repeats the cycle
                // If it fails, like this if statement requires, then it tries to see if its a valid identifier
                if(!createVariableToken(currentString) && !createOperatorToken(currentString) && !createFunctionToken(currentString) && !createValueToken(currentString)) {
                    // First we check if this is a identifier init, which is like "int obama = "
                    // We are specifically seeing if that "=" is there so we can create it
                    if(currentString.charAt(currentString.length()-1) == '"') {
                        createIdentifierToken(currentString.replace("=",""));
                        createOperatorToken("=");
                        currentString = "";
                    } else {
                        // If that init identifier failed, we see if it matches any that currently exist
                        // If so, we can use it and add it properly!
                        boolean foundCopy = false;
                        for (Identifier identifier : identifiers) {
                            if (identifier.getIdentifier().equals(currentString)) {
                                foundCopy = true;
                            }
                        }
                        if(foundCopy) {
                            createIdentifierToken(currentString);
                            currentString = "";
                        }
                    }
                } else {
                    // This happens if the Variable, Operation, Or Function operator Succeded
                    currentString = "";
                }
            }
        }
        // This ends when we run out of characters, so all the current String, aka unprocessed Characters should be done
        if(currentString.length() > 1) {
            System.out.println("Error: Unprocessed Characters Remaining! " + currentString);
        }
    }


    // Return True if it can create a valid VariableType Keyword from given Input
    public boolean createVariableToken(String input) {
        switch(input.toUpperCase()) {
            case "STRING":
                analyzedScript.add(KeyWord.STRING);
                return true;
            case "BOOLEAN":
                analyzedScript.add(KeyWord.BOOLEAN);
                return true;
            case "INT":
                analyzedScript.add(KeyWord.INT);
                return true;
            case "FLOAT":
                analyzedScript.add(KeyWord.FLOAT);
                return true;
        }
        return false;
    }


    // Return True if it can create a valid Operator Keyword from given Input
    public boolean createOperatorToken(String input) {
        switch(input) {
            case "++":
                analyzedScript.add(KeyWord.STRING_CONCAT);
                return true;
            case "<":
                analyzedScript.add(KeyWord.LESS_THAN);
                return true;
            case ">":
                analyzedScript.add(KeyWord.GREATER_THAN);
                return true;
            case "<=":
                analyzedScript.add(KeyWord.EQUAL_LESS_THAN);
                return true;
            case ">=":
                analyzedScript.add(KeyWord.EQUAL_GREATER_THAN);
                return true;
            case "==":
                analyzedScript.add(KeyWord.EQUAL);
                return true;
            case "!=":
                analyzedScript.add(KeyWord.NOT_EQUAL);
                return true;
            case "+=":
                analyzedScript.add(KeyWord.SUM);
                return true;
            case "*":
                analyzedScript.add(KeyWord.MULTIPLY);
                return true;
            case "/":
                analyzedScript.add(KeyWord.DIVIDE);
                return true;
            case "^":
                analyzedScript.add(KeyWord.EXPONENTIAL);
                return true;
            case "+":
                analyzedScript.add(KeyWord.ADD);
                return true;
            case "-":
                analyzedScript.add(KeyWord.SUBTRACT);
                return true;
        }
        return false;
    }

    // Return True if it can create a valid Function Keyword from given Input
    public boolean createFunctionToken(String input) {
        switch(input.toUpperCase()) {
            case "IF":
                analyzedScript.add(KeyWord.IF);
                return true;
            case "THEN":
                analyzedScript.add(KeyWord.THEN);
                return true;
            case "ELSE":
                analyzedScript.add(KeyWord.ELSE);
                return true;
            case "END":
                analyzedScript.add(KeyWord.END);
                return true;
            case "WHILE":
                analyzedScript.add(KeyWord.WHILE);
                return true;
            case "DO":
                analyzedScript.add(KeyWord.DO);
                return true;
            case "REPEAT":
                analyzedScript.add(KeyWord.REPEAT);
                return true;
            case "UNTIL":
                analyzedScript.add(KeyWord.UNTIL);
                return true;
            case "PRINT":
                analyzedScript.add(KeyWord.PRINT);
                return true;
            case "=":
                analyzedScript.add(KeyWord.ASSIGN);
                return true;
            case "(":
                analyzedScript.add(KeyWord.LEFT_PARENTHESIS);
                return true;
            case ")":
                analyzedScript.add(KeyWord.RIGHT_PARENTHESIS);
                return true;

        }
        return false;
    }


    // Return True if it can create a valid VariableType Keyword from given Input
    public boolean createIdentifierToken(String input) {
        // If the Variable was already declared, search for it and add it
        for (Identifier identifier : identifiers) {
            if (identifier.getIdentifier().equals(input)) {
                analyzedScript.add(identifier);
                return true;
            }
        }
        // This script checks, since its not already an identifier we know if the previous
        // Keyword entered was a Variable Type, which it should be since int a = 1, we are on a so the previous
        // Keyword should be int, we check that here then create a new identifier and add it to our
        // AnalyzedScript and identifiers list
        if (analyzedScript.size() > 1 && analyzedScript.get(analyzedScript.size() - 1) instanceof KeyWord && ((KeyWord) analyzedScript.get(analyzedScript.size() - 1)).getVariableType() != null) {
            Identifier identifier = new Identifier(((KeyWord) analyzedScript.get(analyzedScript.size() - 1)).getVariableType(), input);
            identifiers.add(identifier);
            analyzedScript.add(identifier);
            return true;
        }
        // If we f*** up this pops up!
        System.out.println("The Identifier " + input + " did not contain a proper initialization");
        System.out.println("The previous keyword " + analyzedScript.get(analyzedScript.size() - 1) + " is not a valid VariableType");
        System.exit(0);
        return false;
    }

    // Return True if it can create a valid Value from given Input
    public boolean createValueToken(String input) {

        if(containsDigit(input)) { // Check if Number
            if(!input.contains("f")) {
                analyzedScript.add(new Value<>(KeyWord.VariableType.NUMBER,Integer.valueOf(input)));
                return true;
            } else {
                analyzedScript.add(new Value<>(KeyWord.VariableType.NUMBER,Float.valueOf(input)));
                return true;
            }
        } else {
            if (input.toUpperCase().equals("TRUE") || input.toUpperCase().equals("FALSE")) { // Check if Boolean
                analyzedScript.add(new Value<>(KeyWord.VariableType.BOOLEAN,input.toUpperCase().equals("TRUE")));
                return true;
            } else {
                // Check if its a string
                if (input.charAt(0) == '"') {
                    analyzedScript.add(new Value<>(KeyWord.VariableType.STRING,input.replace("\"","")));
                    return true;
                }
            }
        }

        return false;

    }

    public final boolean containsDigit(String s) {
        boolean containsDigit = false;

        if (s != null && !s.isEmpty()) {
            for (char c : s.toCharArray()) {
                if (containsDigit = Character.isDigit(c)) {
                    break;
                }
            }
        }

        return containsDigit;
    }

    public ArrayList<Object> getAnalyzedScript() {
        return analyzedScript;
    }
}
