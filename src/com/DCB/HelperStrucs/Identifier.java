package com.DCB.HelperStrucs;

/**
 * Variable Names, "Bob" ect
 */
public class Identifier {
    private final KeyWord.VariableType variableType;
    private final String identifier;
    public Identifier(KeyWord.VariableType variableType, String identifier) {
        this.variableType = variableType;
        this.identifier = identifier;
    }

    public KeyWord.VariableType getVariableType() {
        return variableType;
    }

    public String getIdentifier() {
        return identifier;
    }

    @Override
    public String toString() {
        return "[Identifier " + " " + variableType + " " + identifier + "]";

    }
}
