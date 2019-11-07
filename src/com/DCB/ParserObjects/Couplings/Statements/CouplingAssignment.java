package com.DCB.ParserObjects.Couplings.Statements;

import com.DCB.LexicalObjects.KeyWord;
import com.DCB.ParserObjects.CoupledObject;
import com.DCB.ParserObjects.CouplingStatement;
import com.DCB.ParserObjects.Value.Identifiers.IntIdentifierObject;
import com.DCB.ParserObjects.Value.IntValueObject;
import com.DCB.ParserObjects.Value.Wrapper.IntValueWrapper;

import javax.print.attribute.standard.PrinterState;

public class CouplingAssignment extends CouplingStatement implements IntValueObject {
    private final IntIdentifierObject intIdentifierObject;
    private final IntValueObject intValueObject;

    public CouplingAssignment(IntIdentifierObject intIdentifierObject, IntValueObject intValueObject) {
        super(CoupleObjectType.ASSIGN);
        this.intIdentifierObject = intIdentifierObject;
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
        String grammer = "";
        if (!isLateStatement()) {
            grammer += "<block> -> <statement> <block> \n";
        } else {
            grammer += "<block> -> <statement> \n";
        }

        grammer += "<statement> -> <assignment_statement> \n" +
                "<assignment_statement> -> id assignment_operator <arithmetic_expression> \n" +
                intValueObject.getParsedGrammar();
        return grammer;
    }
}