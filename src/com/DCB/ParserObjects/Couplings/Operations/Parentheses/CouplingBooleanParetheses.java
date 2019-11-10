package com.DCB.ParserObjects.Couplings.Operations.Parentheses;

import com.DCB.LexicalObjects.KeyWord;
import com.DCB.ParserObjects.CoupledObject;
import com.DCB.ParserObjects.Value.BooleanValueObject;

public class CouplingBooleanParetheses extends CoupledObject implements BooleanValueObject {
    private final boolean value;
    private final BooleanValueObject booleanValueObject;

    public CouplingBooleanParetheses(BooleanValueObject booleanValueObject) {
        super(CoupleObjectType.BOOLEAN_PARENTHESES);
        this.value = booleanValueObject.getValue();
        this.booleanValueObject = booleanValueObject;
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
    public boolean getValue() {
        return value;
    }

    @Override
    public String getStringIdentifier() {
        return "[" + coupleObjectType + " | " + booleanValueObject.getStringIdentifier() + "]";
    }

    @Override
    public String getParsedGrammar() {
        return booleanValueObject.getParsedGrammar();
    }
}

