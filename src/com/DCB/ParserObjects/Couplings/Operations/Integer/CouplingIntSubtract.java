package com.DCB.ParserObjects.Couplings.Operations.Integer;

import com.DCB.LexicalObjects.KeyWord;
import com.DCB.ParserObjects.CoupledObject;
import com.DCB.ParserObjects.Value.IntValueObject;
import com.DCB.ParserObjects.Value.StringValueObject;

/**
 * Coupling that supports the Integer Subtract Operation
 */
public class CouplingIntSubtract extends CoupledObject implements IntValueObject {
    private final int value;
    private final IntValueObject number1;
    private final IntValueObject number2;

    public CouplingIntSubtract(IntValueObject number1, IntValueObject number2) {
        super(CoupleObjectType.NUMBER_SUBTRACT);
        value = number1.getValue() - number2.getValue();
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
        return null

                ;
    }


    @Override
    public int getValue() {
        return value;
    }


}