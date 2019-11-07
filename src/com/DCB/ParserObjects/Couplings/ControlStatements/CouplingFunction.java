package com.DCB.ParserObjects.Couplings.ControlStatements;

import com.DCB.LexicalObjects.KeyWord;
import com.DCB.ParserObjects.CoupledObject;
import com.DCB.ParserObjects.CouplingStatement;

import java.util.ArrayList;

public class CouplingFunction extends CoupledObject {
    private final ArrayList<CouplingStatement> containedStatements;
    public CouplingFunction(ArrayList<CouplingStatement> containedStatements) {
        super(CoupleObjectType.FUNCTION);
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
        identifier += "[" + coupleObjectType + " | ";
        for(int i = 0; i < containedStatements.size(); i++) {
            identifier += "~     " + containedStatements.get(i).getStringIdentifier();
        }
        return identifier;
    }

    @Override
    public String getParsedGrammar() {
        String grammer = "<program> -> function id (block) end \n" +
                "<block> -> <statement> | <statement> <block> \n";
                for(CouplingStatement couplingStatement: containedStatements) {
                    grammer += couplingStatement.getParsedGrammar();
                }
        return grammer;
    }
}
