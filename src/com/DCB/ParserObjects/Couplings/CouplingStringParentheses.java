package com.DCB.ParserObjects.Couplings;

import com.DCB.LexicalObjects.KeyWord;
import com.DCB.ParserObjects.CoupledObject;
import com.DCB.ParserObjects.Value.StringValueObject;


/**
 * Coupling that supports the String Parentheses Operation
 */
public class CouplingStringParentheses extends CoupledObject implements StringValueObject {
    private final String value;
    private final StringValueObject stringValueObject;

    public CouplingStringParentheses(StringValueObject stringValueObject) {
        super(CoupleObjectType.STRING_PARENTHESES);
        this.value = stringValueObject.getValue();
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
        return value;
    }

    @Override
    public String getStringIdentifier() {
        return "[" + coupleObjectType + " | " + stringValueObject.getStringIdentifier() + "]";
    }

    @Override
    public String getParsedGrammar() {
        return null;
    }
}
