package com.DCB.ParserObjects.Couplings;

import com.DCB.LexicalObjects.KeyWord;
import com.DCB.ParserObjects.CoupledObject;
import com.DCB.ParserObjects.Value.StringValueObject;


/**
 * Coupling that supports the String concat operation
 */
public class CouplingStringConcat extends CoupledObject implements StringValueObject {
    private final String value;
    private final StringValueObject string1;
    private final StringValueObject string2;

    public CouplingStringConcat(StringValueObject string1, StringValueObject string2) {
        super(CoupleObjectType.STRING_CONCAT);
        value = string1.getValue() + string2.getValue();
        this.string1 = string1;
        this.string2 = string2;
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
    public String getStringIdentifier() {
        return "[" + coupleObjectType + " | " + string1.getStringIdentifier() + " | " + string2.getStringIdentifier() + " ]";
    }

    @Override
    public String getParsedGrammar() {
        return "";
    }


    @Override
    public String getValue() {
        return value;
    }


}
