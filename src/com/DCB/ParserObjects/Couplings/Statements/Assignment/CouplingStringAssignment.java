package com.DCB.ParserObjects.Couplings.Statements.Assignment;

import com.DCB.LexicalObjects.KeyWord;
import com.DCB.ParserObjects.CouplingStatement;
import com.DCB.ParserObjects.Value.Identifiers.IntIdentifierObject;
import com.DCB.ParserObjects.Value.Identifiers.StringIdentifierObject;
import com.DCB.ParserObjects.Value.IntValueObject;
import com.DCB.ParserObjects.Value.StringValueObject;

public class CouplingStringAssignment extends CouplingStatement implements StringValueObject {
    private final StringIdentifierObject stringIdentifierObject;
    private final StringValueObject stringValueObject;

    public CouplingStringAssignment(StringIdentifierObject stringIdentifierObject, StringValueObject stringValueObject) {
        super(CoupleObjectType.ASSIGN_STRING);
        this.stringIdentifierObject = stringIdentifierObject;
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
        return stringValueObject.getValue();
    }

    @Override
    public String getStringIdentifier() {
        return "[" + coupleObjectType + " | ID:" + stringIdentifierObject.getStringIdentifier() + " Value:" +  stringValueObject.getStringIdentifier() + " ]";
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
                stringValueObject.getParsedGrammar();
        return grammer;
    }
}