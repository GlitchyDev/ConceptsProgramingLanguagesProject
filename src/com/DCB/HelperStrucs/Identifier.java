package com.DCB.HelperStrucs;

/**
 * Variable Names, "Bob" ect
 */
public class Identifier {
    private final KeyWords.VariableType variableType;
    private final String identifier;
    public Identifier(KeyWords.VariableType variableType, String identifier) {
        this.variableType = variableType;
        this.identifier = identifier;
    }

    public KeyWords.VariableType getVariableType() {
        return variableType;
    }

    public String getIdentifier() {
        return identifier;
    }
}
