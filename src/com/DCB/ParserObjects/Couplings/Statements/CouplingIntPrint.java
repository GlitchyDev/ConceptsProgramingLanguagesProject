package com.DCB.ParserObjects.Couplings.Statements;

import com.DCB.LexicalObjects.KeyWord;
import com.DCB.ParserObjects.CoupledObject;
import com.DCB.ParserObjects.Value.IntValueObject;
import com.DCB.ParserObjects.Value.StringValueObject;

public class CouplingIntPrint extends CoupledObject implements IntValueObject {
    private final IntValueObject intValueObject;

    public CouplingIntPrint(IntValueObject intValueObject) {
        super(CoupleObjectType.PRINT_INT);
        this.intValueObject = intValueObject;
    }

    @Override
    public boolean hasReturnType() {
        return false;
    }

    @Override
    public KeyWord.VariableType getReturnType() {
        return null;
    }

    @Override
    public int getValue() {
        return 0;
    }

    @Override
    public String getStringIdentifier() {
        return null;
    }

    @Override
    public String getParsedGrammar() {
        return "<statement> -> <print_statement>\n"
                + "<print_statement> -> print(<arithmetic_expression>)\n"
                ;
    }
}
