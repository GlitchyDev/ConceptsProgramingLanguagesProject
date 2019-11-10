package com.DCB.ParserObjects.Couplings.ControlStatements;

import com.DCB.LexicalObjects.KeyWord;
import com.DCB.ParserObjects.CouplingStatement;
import com.DCB.ParserObjects.Value.BooleanValueObject;

import java.util.ArrayList;

public class CouplingWhileStatement extends CouplingStatement {
    private final BooleanValueObject booleanValueObject;
    private final ArrayList<CouplingStatement> containedStatements;

    public CouplingWhileStatement(BooleanValueObject booleanValueObject, ArrayList<CouplingStatement> containedStatements) {
        super(CoupleObjectType.WHILE);
        this.booleanValueObject = booleanValueObject;
        this.containedStatements = containedStatements;
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
    public String getStringIdentifier() {
        String identifier = "";
        identifier += "[" + coupleObjectType + " \n";
        identifier += " Boolean Value: " + booleanValueObject.getStringIdentifier() + " \n";
        identifier += " Contained Statements\n";
        for(int i = 0; i < containedStatements.size(); i++) {
            identifier += "~    " + containedStatements.get(i).getStringIdentifier() + "\n";
        }
        identifier += "]\n";
        return identifier;
    }

    @Override
    public String getParsedGrammar() {
        return "I CANT DO THIS AHHHH~!!!!";
    }
}

