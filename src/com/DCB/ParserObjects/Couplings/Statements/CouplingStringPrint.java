package com.DCB.ParserObjects.Couplings.Statements;

import com.DCB.LexicalObjects.KeyWord;
import com.DCB.ParserObjects.CouplingStatement;
import com.DCB.ParserObjects.Value.StringValueObject;

public class CouplingStringPrint extends CouplingStatement implements StringValueObject {
    private final StringValueObject stringValueObject;

    public CouplingStringPrint(StringValueObject stringValueObject) {
        super(CoupleObjectType.PRINT_INT);
        this.stringValueObject = stringValueObject;
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
    public String getValue() {
        return null;
    }

    @Override
    public String getStringIdentifier() {
        return "[" + coupleObjectType + " | " + stringValueObject.getStringIdentifier() + " ]";
    }

    @Override
    public String getParsedGrammar() {
        String grammer = "";
        if(!isLateStatement()) {
            grammer += "<block> -> <statement> <block> \n";
        } else {
            grammer += "<block> -> <statement> \n";
        }

        grammer += "<statement> -> <print_statement> \n" +
                "<print_statement> -> print (<arithmetic_expression) \n"
                + stringValueObject.getParsedGrammar();
        return grammer;
    }
}
