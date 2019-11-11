package com.DCB.ParserObjects.Couplings.Statements.Print;

import com.DCB.LexicalObjects.KeyWord;
import com.DCB.ParserObjects.CouplingStatement;
import com.DCB.ParserObjects.Value.IntValueObject;

public class CouplingIntPrint extends CouplingStatement implements IntValueObject {
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
        return "[" + coupleObjectType + " | ID:" + intValueObject.getStringIdentifier() + " ]";
    }

    @Override
    public String getParsedGrammar() {
        String grammer = "";
        if(!isLateStatement()) {
            grammer += "\n<block> -> <statement> <block> \n";
        } else {
            grammer += "\n<block> -> <statement> \n";
        }

        grammer += "<statement> -> <print_statement> \n" +
                "<print_statement> -> print (<arithmetic_expression>) \n"
                + intValueObject.getParsedGrammar();
        return grammer;
    }
}
