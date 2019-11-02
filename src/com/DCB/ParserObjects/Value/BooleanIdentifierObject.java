package com.DCB.ParserObjects.Value;

import com.DCB.LexicalObjects.Identifier;
import com.DCB.LexicalObjects.KeyWord;
import com.DCB.ParserObjects.CoupledObject;

public class BooleanIdentifierObject extends CoupledObject implements BooleanValueObject {
    private final Identifier identifier;
    private final BooleanValueObject booleanValueObject;
    public BooleanIdentifierObject(Identifier identifier, BooleanValueObject booleanValueObject) {
        super(CoupleObjectType.BOOLEAN_IDENTIFIER);
        this.identifier = identifier;
        this.booleanValueObject = booleanValueObject;
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
    public boolean getValue() {
        return booleanValueObject.getValue();
    }
}
