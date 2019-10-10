package com.DCB.HelperStrucs;

/**
 * Variable Values
 * @param <T>
 */
public class Value<T> {
    private final KeyWord.VariableType variableType;
    private final T value;
    public Value(KeyWord.VariableType variableType, T value) {
        this.variableType = variableType;
        this.value = value;
    }

    public KeyWord.VariableType getVariableType() {
        return variableType;
    }

    public T getValue() {
        return value;
    }
}
