package com.DCB.ParserObjects.Value.Identifiers;

import com.DCB.LexicalObjects.Identifier;
import com.DCB.LexicalObjects.KeyWord;
import com.DCB.ParserObjects.CoupledObject;
import com.DCB.ParserObjects.Value.IntValueObject;

/**
 * Coupling that supports identifier objects, and keep tracks of their values
 */
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

    @Override
    public String getStringIdentifier() {
        return "[" + coupleObjectType + " | " + identifier + " ]";
    }

    @Override
    public String getParsedGrammar() {
        return "";
    }
}
