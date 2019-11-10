package com.DCB.ParserObjects.Couplings.Statements.Print;

import com.DCB.LexicalObjects.KeyWord;
import com.DCB.ParserObjects.CouplingStatement;
import com.DCB.ParserObjects.Value.BooleanValueObject;
import com.DCB.ParserObjects.Value.IntValueObject;

public class CouplingBooleanPrint extends CouplingStatement implements IntValueObject {
    private final BooleanValueObject booleanValueObject;

    public CouplingBooleanPrint(BooleanValueObject booleanValueObject) {
        super(CoupleObjectType.PRINT_BOOLEAN);
        this.booleanValueObject = booleanValueObject;
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
        return "[" + coupleObjectType + " | ID:" + booleanValueObject.getStringIdentifier() + " ]";
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
                + booleanValueObject.getParsedGrammar();
        return grammer;
    }
}
