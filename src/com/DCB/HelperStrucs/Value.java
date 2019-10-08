package com.DCB.HelperStrucs;

/**
 * Variable Values
 * @param <T>
 */
public class Value<T> {
    private final KeyWords.VariableType variableType;
    private final T value;
    public Value(KeyWords.VariableType variableType, T value) {
        this.variableType = variableType;
        this.value = value;
    }

    public KeyWords.VariableType getVariableType() {
        return variableType;
    }

    public T getValue() {
        return value;
    }
}
