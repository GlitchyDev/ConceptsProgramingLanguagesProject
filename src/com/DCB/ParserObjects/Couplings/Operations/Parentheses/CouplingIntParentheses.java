package com.DCB.ParserObjects.Couplings.Operations.Parentheses;

import com.DCB.LexicalObjects.KeyWord;
import com.DCB.ParserObjects.CoupledObject;
import com.DCB.ParserObjects.Value.IntValueObject;


/**
 * Coupling that supports the Integer Parentheses operation
 */
public class CouplingIntParentheses extends CoupledObject implements IntValueObject {
    private final int value;
    private final IntValueObject intValueObject;

    public CouplingIntParentheses(IntValueObject intValueObject) {
        super(CoupleObjectType.INT_PARENTHESES);
        this.value = intValueObject.getValue();
        this.intValueObject = intValueObject;
    }

    @Override
    public boolean hasReturnType() {
        return true;
    }

    @Override
    public KeyWord.VariableType getReturnType() {
        return KeyWord.VariableType.NUMBER;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public String getStringIdentifier() {
        return "[" + coupleObjectType + " | " + intValueObject.getStringIdentifier() + "]";
    }

    @Override
    public String getParsedGrammar() {
        return intValueObject.getParsedGrammar();
    }
}