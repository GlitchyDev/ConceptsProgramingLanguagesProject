package com.DCB.ParserObjects.Couplings;

import com.DCB.LexicalObjects.KeyWord;
import com.DCB.ParserObjects.CoupledObject;
import com.DCB.ParserObjects.Value.StringValueObject;

public class CouplingStringConcat extends CoupledObject implements StringValueObject {
    private final String value;

    public CouplingStringConcat(StringValueObject string1, StringValueObject string2) {
        super(CoupleObjectType.STRING_CONCAT);
        value = string1.getValue() + string2.getValue();
    }

    @Override
    public boolean hasReturnType() {
        return true;
    }

    @Override
    public KeyWord.VariableType getReturnType() {
        return KeyWord.VariableType.STRING;
    }


    @Override
    public String getValue() {
        return value;
    }
}
