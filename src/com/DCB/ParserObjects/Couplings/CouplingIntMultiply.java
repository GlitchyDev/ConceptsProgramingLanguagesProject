package com.DCB.ParserObjects.Couplings;

import com.DCB.LexicalObjects.KeyWord;
import com.DCB.ParserObjects.CoupledObject;
import com.DCB.ParserObjects.Value.IntValueObject;

/**
 * Coupling that supports the Integer Multiply Operation
 */
public class CouplingIntMultiply extends CoupledObject implements IntValueObject {
    private final int value;
    private final IntValueObject number1;
    private final IntValueObject number2;

    public CouplingIntMultiply(IntValueObject number1, IntValueObject number2) {
        super(CoupleObjectType.NUMBER_MULTIPLY);
        value = number1.getValue() * number2.getValue();
        this.number1 = number1;
        this.number2 = number2;
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
    public String getStringIdentifier() {
        return "[" + coupleObjectType + " | " + number1.getStringIdentifier() + " | " + number2.getStringIdentifier() + " ]";
    }

    @Override
    public String getParsedGrammar() {
        return "";
    }


    @Override
    public int getValue() {
        return value;
    }


}

