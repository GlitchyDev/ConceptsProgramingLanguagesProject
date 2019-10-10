package com.DCB.HelperStrucs;


/**
 * Keywords are everything, Variables and Operations
 *
 * They can be one or the other, so there is a isOperation() to differentiate.
 *
 * Variables are required to give a broad VariableType, which limits the operations it can be involved in without explicit casting ( String + Number ) and its construction
 *
 * Operations are required to give a broad OperationType for input and out, which limits the inputs output of the Operation, as Number, Boolean, or String
 *
 * Functions are special and require a general function type to describe their function
 */
public enum KeyWord {
    // Variable Types
    STRING(VariableType.STRING),
    BOOLEAN(VariableType.BOOLEAN),
    INT(VariableType.NUMBER),
    FLOAT(VariableType.NUMBER),

    // Operations
    // String Operations
    STRING_CONCAT(OperationType.STRING, OperationType.STRING),
    // Boolean Operations
    LESS_THAN(OperationType.BOOLEAN, OperationType.BOOLEAN),
    GREATER_THAN(OperationType.NUMBER, OperationType.BOOLEAN),
    EQUAL_LESS_THAN(OperationType.NUMBER, OperationType.BOOLEAN),
    EQUAL_GREATER_THAN(OperationType.NUMBER, OperationType.BOOLEAN),
    EQUAL(OperationType.TYPELESS, OperationType.BOOLEAN),
    NOT_EQUAL(OperationType.TYPELESS, OperationType.BOOLEAN),
    SUM(OperationType.NUMBER, OperationType.NUMBER),
    // Number Operations
    MULTIPLY(OperationType.NUMBER, OperationType.NUMBER),
    DIVIDE(OperationType.NUMBER, OperationType.NUMBER),
    EXPONENTIAL(OperationType.NUMBER, OperationType.NUMBER),
    ADD(OperationType.NUMBER, OperationType.NUMBER),
    SUBTRACT(OperationType.NUMBER, OperationType.NUMBER),




    // Function Types
    IF(FunctionType.CONDITIONAL),
    THEN(FunctionType.ENCAPSULATION), // Ends If State Conditional
    ELSE(FunctionType.CONDITIONAL),
    END(FunctionType.ENCAPSULATION), // Ends a "If(bla) {    } <===
    WHILE(FunctionType.CONDITIONAL),
    DO(FunctionType.ENCAPSULATION), // Ends While Statement Conditional
    REPEAT(FunctionType.CONDITIONAL), // Do Repeat Stuff
    UNTIL(FunctionType.CONDITIONAL), // Logic that runs Repeat
    PRINT(FunctionType.OUTPUT),
    ASSIGN(FunctionType.ASSIGNMENT),
    LEFT_PARENTHESIS(FunctionType.ENCAPSULATION),
    RIGHT_PARENTHESIS(FunctionType.ENCAPSULATION),



            ;


    private final VariableType variableType;
    private final OperationType inputOperationType;
    private final OperationType outputOperationType;
    private final FunctionType functionType;

    KeyWord(VariableType variableType) {
        this.variableType = variableType;
        this.inputOperationType = null;
        this.outputOperationType = null;
        this.functionType = null;
    }

    KeyWord(OperationType inputOperationType, OperationType outputOperationType) {
        this.variableType = null;
        this.inputOperationType = inputOperationType;
        this.outputOperationType = outputOperationType;
        this.functionType = null;
    }

    KeyWord(FunctionType functionType) {
        this.variableType = null;
        this.inputOperationType = null;
        this.outputOperationType = null;
        this.functionType = functionType;
    }

    public KeyWordType getKeyWordType() {
        if(variableType != null) {
            return KeyWordType.VARIABLE;
        }
        if(inputOperationType != null) {
            return KeyWordType.OPERATION;
        }
        if(functionType != null) {
            return KeyWordType.FUNCTION;
        }
        return null;
    }

    public VariableType getVariableType() {
        return variableType;
    }

    public OperationType getInputOperationType() {
        return inputOperationType;
    }
    public OperationType getOutputOperationType() {
        return outputOperationType;
    }


    public FunctionType getFunctionType() {
        return functionType;
    }


    public enum KeyWordType {
        VARIABLE,
        OPERATION,
        FUNCTION,
    }

    public enum VariableType {
        STRING,
        BOOLEAN,
        NUMBER
    }

    public enum OperationType {
        STRING,
        BOOLEAN,
        NUMBER,
        TYPELESS,
    }

    public enum FunctionType {
        CONDITIONAL, // If, Else, Switch
        OUTPUT, // Print
        ASSIGNMENT, // =
        ENCAPSULATION,
    }
}