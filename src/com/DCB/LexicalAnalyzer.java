package com.DCB;

import com.DCB.LexicalObjects.Identifier;
import com.DCB.LexicalObjects.KeyWord;
import com.DCB.LexicalObjects.Value;

import java.util.ArrayList;

/*
 * Class:       CS 4308 Section 2
 * Term:        Fall 2019
 * Name:        Robert, Chris, James
 * Instructor:   Deepa Muralidhar
 * Project:  Deliverable 1 Scanner - Java
 */

public class LexicalAnalyzer {
    // Every token we have currently analyzed, can be of object type KeyWord, Value, or Identifier
    private final ArrayList<Object> analyzedScript = new ArrayList<>();
    private final ArrayList<Integer> scriptLines = new ArrayList<>();

    // Keeping track of our Identifiers, aka Variables as they are declared so we can reuse their objects across each instance
    private final ArrayList<Identifier> identifiers = new ArrayList<>();


    // Store the script Reader as an class instance variable so the Number override can check it
    private final ScriptReader scriptReader;
    // Current String of characters we are seeing is a valid keyword
    private String currentString;
    // A special boolean that trips when a Number grabs a extra character to see when the end of the number is
    // By tripping this we know an extra character was taken and can compensate
    private boolean numberOverride;
    private int currentLineNumber = 0;

    public LexicalAnalyzer(ScriptReader scriptReader) {
        this.scriptReader = scriptReader;
        currentString = "";
        numberOverride = false;
        // We keep track of every failed character we have pulled out till we get a success or run out of characters
        // Keep going on the loop till we run out of document
        // If there was a number override, we also check since a extra character has been stored
        while(scriptReader.hasNextChar() || numberOverride) {
            // If there is a number override we check the char we stored first instead, incase its a single char lexime
            if(!numberOverride) {
                char c = scriptReader.getNextChar();
                // We ignore spaces like a bad ass :O
                if(c == '\n') {
                    currentLineNumber++;
                }
                if (c != ' ' && c != '\n' && c != '\r' && c != '\t') {
                    // Add this character to our current collection of characters
                    currentString += c;
                }
            }
            // We negate the number override if it happened
            numberOverride = false;

            // This if statement will check and see if it can create a valid VariableType, Operator, Function Token, or Value
            // From the Given input, if it passes, it resets the currentString and repeats the cycle
            // If it fails, like this if statement requires, then it tries to see if its a valid identifier
            if (currentString.length() > 0) {
                if (!createOperatorToken(currentString) && !createFunctionToken(currentString) && !createValueToken(currentString)) {
                    // First we check if this is a identifier init, which is like "int obama = "
                    // We are specifically seeing if that "=" is there so we can create it
                    boolean foundCopy = false;
                    for (Identifier identifier : identifiers) {
                        if (identifier.getIdentifier().equals(currentString)) {
                            foundCopy = true;
                        }
                    }
                    if (foundCopy) {
                        createIdentifierToken(currentString);
                        currentString = "";
                    } else {
                        if (currentString.charAt(currentString.length() - 1) == '=') {
                            createIdentifierToken(currentString.replace("=", ""));
                            createFunctionToken("=");
                            currentString = "";
                        } else {
                            // Can't do anything yet
                        }
                    }
                } else {
                    // This happens if the Variable, Operation, Or Function operator Succeded
                    if (!numberOverride) {
                        currentString = "";
                    }

                }
            }
        }
            // This ends when we run out of characters, so all the current String, aka unprocessed Characters should be done
        if (currentString.length() >= 1) {
            System.out.println("Error: Unprocessed Characters Remaining of count " + currentString.length());
            System.out.println("Invalid:" + currentString);
        }
    }




    // Return True if it can create a valid Operator Keyword from given Input
    public boolean createOperatorToken(String input) {
        switch(input) {
            case "<":
                analyzedScript.add(KeyWord.LESS_THAN);
                scriptLines.add(currentLineNumber);
                return true;
            case ">":
                analyzedScript.add(KeyWord.GREATER_THAN);
                scriptLines.add(currentLineNumber);
                return true;
            case "<=":
                analyzedScript.add(KeyWord.EQUAL_LESS_THAN);
                scriptLines.add(currentLineNumber);
                return true;
            case ">=":
                analyzedScript.add(KeyWord.EQUAL_GREATER_THAN);
                scriptLines.add(currentLineNumber);
                return true;
            case "==":
                analyzedScript.add(KeyWord.EQUAL);
                scriptLines.add(currentLineNumber);
                return true;
            case "!=":
                analyzedScript.add(KeyWord.NOT_EQUAL);
                scriptLines.add(currentLineNumber);
                return true;
            case "*":
                analyzedScript.add(KeyWord.MULTIPLY);
                scriptLines.add(currentLineNumber);
                return true;
            case "/":
                analyzedScript.add(KeyWord.DIVIDE);
                scriptLines.add(currentLineNumber);
                return true;
            case "^":
                analyzedScript.add(KeyWord.EXPONENTIAL);
                scriptLines.add(currentLineNumber);
                return true;
            case "+":
                analyzedScript.add(KeyWord.ADD);
                scriptLines.add(currentLineNumber);
                return true;
            case "-":
                analyzedScript.add(KeyWord.SUBTRACT);
                scriptLines.add(currentLineNumber);
                return true;
            case "\\":
                analyzedScript.add(KeyWord.INVERT_DIVIDE);
                scriptLines.add(currentLineNumber);
                return true;
            case "%":
                analyzedScript.add(KeyWord.MOD);
                scriptLines.add(currentLineNumber);
                return true;
            case "&&":
                analyzedScript.add(KeyWord.AND);
                scriptLines.add(currentLineNumber);
                return true;
        }
        return false;
    }

    // Return True if it can create a valid Function Keyword from given Input
    public boolean createFunctionToken(String input) {
        switch(input.toUpperCase()) {
            case "IF":
                analyzedScript.add(KeyWord.IF);
                scriptLines.add(currentLineNumber);
                return true;
            case "THEN":
                analyzedScript.add(KeyWord.THEN);
                scriptLines.add(currentLineNumber);
                return true;
            case "ELSE":
                analyzedScript.add(KeyWord.ELSE);
                scriptLines.add(currentLineNumber);
                return true;
            case "END":
                analyzedScript.add(KeyWord.END);
                scriptLines.add(currentLineNumber);
                return true;
            case "WHILE":
                analyzedScript.add(KeyWord.WHILE);
                scriptLines.add(currentLineNumber);
                return true;
            case "DO":
                analyzedScript.add(KeyWord.DO);
                scriptLines.add(currentLineNumber);
                return true;
            case "REPEAT":
                analyzedScript.add(KeyWord.REPEAT);
                scriptLines.add(currentLineNumber);
                return true;
            case "UNTIL":
                analyzedScript.add(KeyWord.UNTIL);
                scriptLines.add(currentLineNumber);
                return true;
            case "PRINT":
                analyzedScript.add(KeyWord.PRINT);
                scriptLines.add(currentLineNumber);
                return true;
            case "=":
                analyzedScript.add(KeyWord.ASSIGN);
                scriptLines.add(currentLineNumber);
                return true;
            case "(":
                analyzedScript.add(KeyWord.LEFT_PARENTHESIS);
                scriptLines.add(currentLineNumber);
                return true;
            case ")":
                analyzedScript.add(KeyWord.RIGHT_PARENTHESIS);
                scriptLines.add(currentLineNumber);
                return true;
            case "FOR":
                analyzedScript.add(KeyWord.FOR);
                scriptLines.add(currentLineNumber);
                return true;
            case "FOR EACH":
                analyzedScript.add(KeyWord.FOR_EACH);
                scriptLines.add(currentLineNumber);
                return true;

        }
        return false;
    }


    // Return True if it can create a valid VariableType Keyword from given Input
    public boolean createIdentifierToken(String input) {
        if(input.length() == 1) {
            // If the Variable was already declared, search for it and add it
            for (Identifier identifier : identifiers) {
                if (identifier.getIdentifier().equals(input)) {
                    analyzedScript.add(identifier);
                    scriptLines.add(currentLineNumber);
                    return true;
                }
            }
            // This script checks, since its not already an identifier we know if the previous
            // Keyword entered was a Variable Type, which it should be since int a = 1, we are on a so the previous
            // Keyword should be int, we check that here then create a new identifier and add it to our
            // AnalyzedScript and identifiers list

            Identifier identifier = new Identifier(KeyWord.VariableType.TYPELESS, input.replace("=", ""));
            identifiers.add(identifier);
            analyzedScript.add(identifier);
            scriptLines.add(currentLineNumber);
        } else {
            System.out.println("Undertermined Character. Valid identifiers can't be greater than 1 long");
            System.exit(0);
        }
        return false;
    }

    public void checkIdentifier() {
        Value value = (Value) analyzedScript.get(analyzedScript.size()-1);
        if(analyzedScript.size() > 3) {
            if(analyzedScript.get(analyzedScript.size()-2) instanceof KeyWord && analyzedScript.get(analyzedScript.size()-2) == KeyWord.ASSIGN) {
                if(analyzedScript.get(analyzedScript.size()-3) instanceof Identifier) {
                    ((Identifier) analyzedScript.get(analyzedScript.size()-3)).setVariableType(value.getVariableType());
                    ((Identifier) analyzedScript.get(analyzedScript.size()-3)).setValue(value);
                }
            }
        }
    }
    // Return True if it can create a valid Value from given Input
    public boolean createValueToken(String input) {

        // Check if the input has a " on each side of the input string, representing a String
        if (input.charAt(0) == '"' && input.length() > 1 && input.charAt(input.length() - 1) == '"') {
            analyzedScript.add(new Value<>(KeyWord.VariableType.STRING, input.replace("\"", "")));
            scriptLines.add(currentLineNumber);
            checkIdentifier();
            return true;
        } else {
            // If we know its not a string, lets see if it contains numbers, and if it is a number input
            if (Character.isDigit(input.charAt(0))) { // Check if Number
                // We check if the last letter of this input string is a nonnumber that isn't a period or there are no more characters
                // If so, we can assume this number has ended and this can process it
                if (!Character.isDigit(input.charAt(input.length() - 1)) && input.charAt(input.length() - 1) != '.' || !scriptReader.hasNextChar()) {
                    if (!input.contains(".")) {
                        // We process one way if we know that there is an additional char, otherwise we know
                        // The number is the last thing in the document and need to do it a different way
                        if (scriptReader.hasNextChar()) {
                            char store = input.charAt(input.length() - 1);
                            input = input.replace("" + store, "");
                            analyzedScript.add(new Value<>(KeyWord.VariableType.NUMBER, Integer.valueOf(input)));
                            scriptLines.add(currentLineNumber);
                            checkIdentifier();
                            this.numberOverride = true;
                            currentString = "" + store;
                            return true;
                        } else {
                            if (!Character.isDigit(input.charAt(input.length() - 1))) {
                                char store = input.charAt(input.length() - 1);
                                input = input.replace("" + store, "");
                                analyzedScript.add(new Value<>(KeyWord.VariableType.NUMBER, Integer.valueOf(input)));
                                scriptLines.add(currentLineNumber);
                                checkIdentifier();
                                this.numberOverride = true;
                                currentString = "" + store;
                                return true;
                            } else {
                                analyzedScript.add(new Value<>(KeyWord.VariableType.NUMBER, Integer.valueOf(input)));
                                scriptLines.add(currentLineNumber);
                                checkIdentifier();
                                return true;
                            }
                        }

                    } else {
                        // We process one way if we know that there is an additional char, otherwise we know
                        // The number is the last thing in the document and need to do it a different way
                        if (scriptReader.hasNextChar()) {
                            char store = input.charAt(input.length() - 1);
                            input = input.replace("" + store, "");
                            analyzedScript.add(new Value<>(KeyWord.VariableType.NUMBER, Integer.valueOf(input)));
                            scriptLines.add(currentLineNumber);
                            checkIdentifier();
                            this.numberOverride = true;
                            currentString = "" + store;
                            return true;
                        } else {
                            if (!Character.isDigit(input.charAt(input.length() - 1))) {
                                char store = input.charAt(input.length() - 1);
                                input = input.replace("" + store, "");
                                analyzedScript.add(new Value<>(KeyWord.VariableType.NUMBER, Integer.valueOf(input)));
                                scriptLines.add(currentLineNumber);
                                checkIdentifier();
                                this.numberOverride = true;
                                currentString = "" + store;
                                return true;
                            } else {
                                analyzedScript.add(new Value<>(KeyWord.VariableType.NUMBER, Integer.valueOf(input)));
                                scriptLines.add(currentLineNumber);
                                checkIdentifier();
                                return true;
                            }
                        }
                    }
                }
            } else {
                if (input.toUpperCase().equals("TRUE") || input.toUpperCase().equals("FALSE")) { // Check if Boolean
                    analyzedScript.add(new Value<>(KeyWord.VariableType.BOOLEAN, input.toUpperCase().equals("TRUE")));
                    scriptLines.add(currentLineNumber);
                    checkIdentifier();
                    return true;
                }
            }
        }

        return false;

    }




    public boolean checkIfNumber(int index) {
        if(index >= 0 && getAnalyzedScript().size() > index) {
            if (getAnalyzedScript().get(index) instanceof Value) {
                if (((Value) getAnalyzedScript().get(index)).getVariableType() == KeyWord.VariableType.NUMBER) {
                    return true;
                }
            } else {
                if (getAnalyzedScript().get(index) instanceof Identifier) {
                    if (((Identifier) getAnalyzedScript().get(index)).getVariableType() == KeyWord.VariableType.NUMBER) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean checkIfString(int index) {
        if(index >= 0 && getAnalyzedScript().size() > index) {
            if (getAnalyzedScript().get(index) instanceof Value) {
                if (((Value) getAnalyzedScript().get(index)).getVariableType() == KeyWord.VariableType.STRING) {
                    return true;
                }
            } else {
                if (getAnalyzedScript().get(index) instanceof Identifier) {
                    if (((Identifier) getAnalyzedScript().get(index)).getVariableType() == KeyWord.VariableType.STRING) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean instanceOfAddKeyword(int index) {
        if(index >= 0 && getAnalyzedScript().size() > index) {
           if(getAnalyzedScript().get(index) instanceof KeyWord)  {
               if(getAnalyzedScript().get(index) == KeyWord.ADD) {
                   return true;
               }
           }
        }
        return false;
    }

    public boolean instanceOfSubtractKeyword(int index) {
        if(index >= 0 && getAnalyzedScript().size() > index) {
            if(getAnalyzedScript().get(index) instanceof KeyWord)  {
                if(getAnalyzedScript().get(index) == KeyWord.SUBTRACT) {
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

    public ArrayList<Integer> getScriptLines() {
        return scriptLines;
    }

    public ArrayList<Identifier> getIdentifiers() {
        return identifiers;
    }

    public int getCurrentLineNumber() {
        return currentLineNumber;
    }
}
