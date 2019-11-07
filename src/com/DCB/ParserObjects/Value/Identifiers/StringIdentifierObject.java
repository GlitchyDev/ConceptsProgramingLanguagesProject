package com.DCB.ParserObjects.Value.Identifiers;

import com.DCB.LexicalObjects.Identifier;
import com.DCB.LexicalObjects.KeyWord;
import com.DCB.ParserObjects.CoupledObject;
import com.DCB.ParserObjects.Value.StringValueObject;

/**
 * Coupling that supports identifier objects, and keep tracks of their values
 */
public class StringIdentifierObject  extends CoupledObject implements StringValueObject {
    private final Identifier identifier;
    private final StringValueObject stringValueObject;
    public StringIdentifierObject(Identifier identifier, StringValueObject stringValueObject) {
        super(CoupleObjectType.STRING_IDENTIFIER);
        this.identifier = identifier;
        this.stringValueObject = stringValueObject;
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
        return stringValueObject.getValue();
    }

    @Override
    public String getStringIdentifier() {
        return "[" + coupleObjectType + " | " + identifier + " ]";
    }

    @Override
    public String getParsedGrammar() {
        return "<arithmetic_expression> -> id \n";
    }
}
