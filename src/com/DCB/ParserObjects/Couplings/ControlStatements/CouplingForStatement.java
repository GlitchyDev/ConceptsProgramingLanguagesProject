package com.DCB.ParserObjects.Couplings.ControlStatements;

import com.DCB.LexicalObjects.KeyWord;
import com.DCB.ParserObjects.CouplingControlStatement;
import com.DCB.ParserObjects.CouplingStatement;
import com.DCB.ParserObjects.Couplings.Statements.Assignment.CouplingIntAssignment;
import com.DCB.ParserObjects.Value.BooleanValueObject;
import com.DCB.ParserObjects.Value.IntValueObject;
import com.sun.jdi.IntegerValue;

import java.util.ArrayList;

public class CouplingForStatement extends CouplingControlStatement {
    private final CouplingIntAssignment couplingIntAssignment;
    private final IntValueObject intValueObject;
    private final ArrayList<CouplingStatement> containedStatements;

    public CouplingForStatement(CouplingIntAssignment couplingIntAssignment, IntValueObject intValueObject, ArrayList<CouplingStatement> containedStatements) {
        super(CoupleObjectType.FOR);
        this.couplingIntAssignment = couplingIntAssignment;
        this.intValueObject = intValueObject;
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
        identifier += " Assignment Statement: " + couplingIntAssignment.getStringIdentifier() + " \n";
        identifier += " Integer Value: " + intValueObject.getStringIdentifier() + " \n";
        identifier += " Contained Statements\n";
        for(int i = 0; i < containedStatements.size(); i++) {
            identifier += "~     " + containedStatements.get(i).getStringIdentifier() + "\n";
        }
        identifier += "]\n";
        return identifier;
    }

    @Override
    public String getParsedGrammar() {
    	
    	String containGrammer = "\n";
    	for (int i = 0; i<containedStatements.size();i++)
    	{
    		containGrammer += containedStatements.get(i).getParsedGrammar();
    	}
    	
    	String grammer = "";
        if(!isLateStatement()) {
            grammer += "\n<block> -> <statement> <block> \n";
        } else {
            grammer += "\n<block> -> <statement> \n";
        }

        grammer += "<statement> -> <for_statement>\r\n"
        		+ "<for_statement> -> for id = <iter> <block> end\n"
                + couplingIntAssignment.getParsedGrammar()
                + intValueObject.getParsedGrammar()
                + containGrammer
                ;
        return grammer;
    }

    public CouplingIntAssignment getCouplingIntAssignment() {
        return couplingIntAssignment;
    }

    public IntValueObject getIntValueObject() {
        return intValueObject;
    }

    public ArrayList<CouplingStatement> getContainedStatements() {
        return containedStatements;
    }

    @Override
    public void setLateStatement() {
        containedStatements.get(containedStatements.size()-1).setLateStatement();
    }
}

