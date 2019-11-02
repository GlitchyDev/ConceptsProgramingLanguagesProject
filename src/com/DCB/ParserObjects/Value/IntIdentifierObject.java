package com.DCB.ParserObjects.Value;

import com.DCB.LexicalObjects.Identifier;
import com.DCB.LexicalObjects.KeyWord;
import com.DCB.ParserObjects.CoupledObject;

public class IntIdentifierObject  extends CoupledObject implements IntValueObject {
    private final Identifier identifier;
    private final IntValueObject intValueObject;
    public IntIdentifierObject(Identifier identifier, IntValueObject intValueObject) {
        super(CoupleObjectType.INT_IDENTIFIER);
        this.identifier = identifier;
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
        return intValueObject.getValue();
    }
}
