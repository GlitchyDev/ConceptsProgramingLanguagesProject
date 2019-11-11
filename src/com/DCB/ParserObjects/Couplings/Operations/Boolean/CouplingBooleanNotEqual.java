package com.DCB.ParserObjects.Couplings.Operations.Boolean;

import com.DCB.LexicalObjects.KeyWord;
import com.DCB.ParserObjects.CoupledObject;
import com.DCB.ParserObjects.Value.BooleanValueObject;
import com.DCB.ParserObjects.Value.IntValueObject;

public class CouplingBooleanNotEqual extends CoupledObject implements BooleanValueObject {
    private final boolean value;
    private final IntValueObject number1;
    private final IntValueObject number2;

    public CouplingBooleanNotEqual(IntValueObject number1, IntValueObject number2) {
        super(CoupleObjectType.BOOLEAN_NOT_EQUAl);
        value = number1.getValue() != number2.getValue();
        this.number1 = number1;
        this.number2 = number2;
    }

    @Override
    public boolean hasReturnType() {
        return true;
    }

    @Override
    public KeyWord.VariableType getReturnType() {
        return KeyWord.VariableType.BOOLEAN;
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
    public boolean getValue() {
        return value;
    }


}