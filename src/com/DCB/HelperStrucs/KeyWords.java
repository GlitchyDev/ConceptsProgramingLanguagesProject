package com.DCB.HelperStrucs;


/**
 * Keywords are everything, Variables and Operations
 *
 * They can be one or the other, so there is a isOperation() to differentiate.
 *
 * Variables are required to give a broad VariableType, which limits the operations it can be involved in without explicit casting ( String + Number ) and its construction
 *
 * Operations are required to give a broad OperationType, which limits the output of the Operation, as Number, Boolean, or String
 */
public enum KeyWords {
    // Variable Types
    STRING(VariableType.STRING),
    CHAR(VariableType.CHAR),
    BOOLEAN(VariableType.NUMBER),
    INT(VariableType.NUMBER),
    FLOAT(VariableType.NUMBER),
    DOUBLE(VariableType.NUMBER),
    LONG(VariableType.NUMBER),
    SHORT(VariableType.NUMBER),
    BYTE(VariableType.NUMBER),

    // Operations
    // String Operations
    STRING_CONCAT(OperationType.STRING),
    // Boolean Operations
    LESS_THAN(OperationType.BOOLEAN),
    GREATER_THAN(OperationType.BOOLEAN),
    EQUAL_OR_LESS_THAN(OperationType.BOOLEAN),
    EQUAL_OR_GREATER_THAN(OperationType.BOOLEAN),
    // Number Operations
    MULTIPLY(OperationType.NUMBER),
    DIVIDE(OperationType.NUMBER),
    EXPONENTIAL(OperationType.NUMBER),
    ADD(OperationType.NUMBER),
    SUBTRACT(OperationType.NUMBER),

    ;


    private final boolean isOperation;
    private final VariableType variableType;
    private final OperationType operationType;
    KeyWords(VariableType variableType) {
        this.isOperation = false;
        this.variableType = variableType;
        this.operationType = null;
    }

    KeyWords(OperationType operationType) {
        this.isOperation = true;
        this.variableType = null;
        this.operationType = operationType;
    }

    public boolean isOperation() {
        return isOperation;
    }

    public VariableType getVariableType() {
        return variableType;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public enum VariableType {
        STRING,
        CHAR,
        BOOLEAN,
        NUMBER
    }

    public enum OperationType {
        STRING,
        BOOLEAN,
        NUMBER
    }

}
