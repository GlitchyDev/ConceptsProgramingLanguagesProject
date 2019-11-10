package com.DCB.ParserObjects.Couplings.ControlStatements;

import com.DCB.LexicalObjects.KeyWord;
import com.DCB.ParserObjects.CoupledObject;
import com.DCB.ParserObjects.CouplingStatement;

import java.util.ArrayList;

public class CouplingIfStatement extends CoupledObject {
    private final ArrayList<CouplingStatement> ifStatements;
    private final ArrayList<CouplingStatement> elseStatements;
    public CouplingIfStatement(ArrayList<CouplingStatement> ifStatements, ArrayList<CouplingStatement> elseStatements) {
        super(CoupleObjectType.IF);
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
        identifier += "[" + coupleObjectType + " \n ";
        identifier += "IF \n";
        for(int i = 0; i < ifStatements.size(); i++) {
            identifier += "~     " + ifStatements.get(i).getStringIdentifier();
        }
        identifier += "ELSE \n";
        for(int i = 0; i < elseStatements.size(); i++) {
            identifier += "~     " + elseStatements.get(i).getStringIdentifier();
        }
        return identifier;
    }

    @Override
    public String getParsedGrammar() {
        String grammer = "<program> -> function id (block) end \n" +
                "<block> -> <statement> | <statement> <block> \n";
        for(CouplingStatement couplingStatement: ifStatements) {
            grammer += couplingStatement.getParsedGrammar();
        }
        for(CouplingStatement couplingStatement: elseStatements) {
            grammer += couplingStatement.getParsedGrammar();
        }
        return grammer;
    }
}
