package com.DCB.ParserObjects.Couplings.ControlStatements;

import com.DCB.LexicalObjects.KeyWord;
import com.DCB.ParserObjects.CoupledObject;
import com.DCB.ParserObjects.CouplingStatement;
import com.DCB.ParserObjects.Value.BooleanValueObject;

import java.util.ArrayList;

public class CouplingIfStatement extends CouplingStatement {
    private final BooleanValueObject booleanValueObject;
    private final ArrayList<CouplingStatement> ifStatements;
    private final ArrayList<CouplingStatement> elseStatements;
    public CouplingIfStatement(BooleanValueObject booleanValueObject, ArrayList<CouplingStatement> ifStatements, ArrayList<CouplingStatement> elseStatements) {
        super(CoupleObjectType.IF);
        this.booleanValueObject = booleanValueObject;
        this.ifStatements = ifStatements;
        this.elseStatements = elseStatements;
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
        identifier += " IF Statements\n";
        for(int i = 0; i < ifStatements.size(); i++) {
            identifier += "~     " + ifStatements.get(i).getStringIdentifier();
        }
        identifier += " ELSE Statements\n";
        for(int i = 0; i < elseStatements.size(); i++) {
            identifier += "~     " + elseStatements.get(i).getStringIdentifier() + "\n";
        }
        identifier += "]\n";
        return identifier;
    }

    @Override
    public String getParsedGrammar() {
        return "I CANT DO THIS AHHHH~!!!!";
    }
}
