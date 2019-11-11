package com.DCB.ParserObjects.Couplings.Statements.Assignment;

import com.DCB.LexicalObjects.KeyWord;
import com.DCB.ParserObjects.CouplingStatement;
import com.DCB.ParserObjects.Value.BooleanValueObject;
import com.DCB.ParserObjects.Value.Identifiers.BooleanIdentifierObject;
import com.DCB.ParserObjects.Value.Identifiers.IntIdentifierObject;
import com.DCB.ParserObjects.Value.IntValueObject;

public class CouplingBooleanAssignment extends CouplingStatement implements BooleanValueObject {
    private final BooleanIdentifierObject booleanIdentifierObject;
    private final BooleanValueObject booleanValueObject;

    public CouplingBooleanAssignment(BooleanIdentifierObject booleanIdentifierObject, BooleanValueObject booleanValueObject) {
        super(CoupleObjectType.ASSIGN_BOOLEAN);
        this.booleanIdentifierObject = booleanIdentifierObject;
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
    public boolean getValue() {
        return booleanValueObject.getValue();
    }

    @Override
    public String getStringIdentifier() {
        return "[" + coupleObjectType + " | ID:" + booleanIdentifierObject.getStringIdentifier() + " Value:" +  booleanValueObject.getStringIdentifier() + " ]";
    }

    @Override
    public String getParsedGrammar() {
        String grammer = "";
        if (!isLateStatement()) {
            grammer += "\n<block> -> <statement> <block> \n";
        } else {
            grammer += "\n<block> -> <statement> \n";
        }

        grammer += "<statement> -> <assignment_statement> \n" +
                "<assignment_statement> -> id assignment_operator <arithmetic_expression> \n" +
                booleanValueObject.getParsedGrammar();
        return grammer;
    }
}