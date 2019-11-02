package com.DCB.ParserObjects.Value;


import com.DCB.LexicalObjects.KeyWord;
import com.DCB.LexicalObjects.Value;
import com.DCB.ParserObjects.CoupledObject;

public class BooleanValueWrapper extends CoupledObject implements BooleanValueObject{
    private final Value value;
    public BooleanValueWrapper(Value value) {
        super(CoupleObjectType.BOOLEAN_VALUE);
        this.value = value;
    }


    @Override
    public boolean getValue() {
        return (boolean) value.getValue();
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
