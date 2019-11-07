package com.DCB.ParserObjects.Value.Wrapper;

import com.DCB.LexicalObjects.KeyWord;
import com.DCB.LexicalObjects.Value;
import com.DCB.ParserObjects.CoupledObject;
import com.DCB.ParserObjects.Value.IntValueObject;

/**
 * Coupling that supports Value objects, and properly encapsulates them
 */
public class IntValueWrapper extends CoupledObject implements IntValueObject {
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

    @Override
    public String getStringIdentifier() {
        return "[" + coupleObjectType + " | " + value + " ]";
    }

    @Override
    public String getParsedGrammar() {
         return "<arithmetic_expression> -> <literal_integer>\n";
    }
}
