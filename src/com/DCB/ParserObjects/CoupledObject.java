package com.DCB.ParserObjects;

import com.DCB.LexicalObjects.KeyWord;


/**
 * This is a Coupling Object that is used to encapsulate all Operations and Statements that allows for proper parsing via
 * the grammer encapsulation assignment method
 */
public abstract class CoupledObject {
    protected final CoupleObjectType coupleObjectType;
    public CoupledObject(CoupleObjectType coupleObjectType) {
        this.coupleObjectType = coupleObjectType;
    }

    public CoupleObjectType getCoupleObjectType() {
        return coupleObjectType;
    }

    public abstract boolean hasReturnType();
    public abstract KeyWord.VariableType getReturnType();

    public abstract String getStringIdentifier();
    public abstract String getParsedGrammar();


    public enum CoupleObjectType {
        BOOLEAN_VALUE,
        INT_VALUE,
        STRING_VALUE,

        BOOLEAN_IDENTIFIER,
        INT_IDENTIFIER,
        STRING_IDENTIFIER,

        BOOLEAN_PARENTHIS,
        INT_PARENTHIS,
        STRING_PARENTHESES,

        STRING_CONCAT,
        BOOLEAN_LESS_THAN,
        BOOLEAN_GREATER_THAN,
        BOOLEAN_EQUAL_LESS_THAN,
        BOOLEAN_EQUAL_GREATER_THAN,
        BOOLEAN_AND,
        BOOLEAN_EQUAl,
        BOOLEAN_NOT_EQUAl,
        NUMBER_ADD,
        NUMBER_SUBTRACT,
        NUMBER_MULTIPLY,
        NUMBER_DIVIDE,
        NUMBER_INVERT_DIVIDE,
        NUMBER_EXPONTENTIAL,
        NUMBER_MOD,
        NUMBER_IDENTITY,
        NUMBER_INVERT,


        IF,
        ELSE,
        WHILE,
        DO,
        REPEAT,
        PRINT_INT,
        PRINT_STRING,
        ASSIGN,
        FOR,
        FOR_EACH
    }
}
