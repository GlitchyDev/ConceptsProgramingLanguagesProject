package com.DCB.ParserObjects.Value;

import com.DCB.LexicalObjects.KeyWord;
import com.DCB.LexicalObjects.Value;
import com.DCB.ParserObjects.CoupledObject;

public class IntValueWrapper extends CoupledObject implements IntValueObject{
    private final Value value;

    public IntValueWrapper(Value value) {
        super(CoupleObjectType.INT_VALUE);
        this.value = value;
    }


    @Override
    public int getValue() {
        return (int) value.getValue();
    }

    @Override
    public boolean hasReturnType() {
        return true;
    }

    @Override
    public KeyWord.VariableType getReturnType() {
        return KeyWord.VariableType.NUMBER;
    }
}
