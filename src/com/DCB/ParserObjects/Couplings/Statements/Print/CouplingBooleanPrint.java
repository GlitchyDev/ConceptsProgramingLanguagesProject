package com.DCB.ParserObjects.Couplings.Statements.Print;

import com.DCB.LexicalObjects.KeyWord;
import com.DCB.ParserObjects.Couplings.Statements.CouplingStatement;
import com.DCB.ParserObjects.Value.BooleanValueObject;
import com.DCB.ParserObjects.Value.Identifiers.IdentifierCoupling;
import com.DCB.ParserObjects.Value.IntValueObject;

import java.util.ArrayList;

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

  //See CouplingForStatement.java for explanation.
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
                + booleanValueObject.getParsedGrammar();
        return grammer;
    }


    @Override
    public void executeStatement() { System.out.println("PRINT: " + booleanValueObject.getValue());

    }
}
