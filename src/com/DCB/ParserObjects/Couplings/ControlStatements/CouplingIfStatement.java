package com.DCB.ParserObjects.Couplings.ControlStatements;

import com.DCB.LexicalObjects.KeyWord;
import com.DCB.ParserObjects.CoupledObject;
import com.DCB.ParserObjects.CouplingControlStatement;
import com.DCB.ParserObjects.CouplingStatement;
import com.DCB.ParserObjects.Value.BooleanValueObject;

import java.util.ArrayList;

/**
 * Control Statement encapsulation coupling for the If Control Statement
 */
public class CouplingIfStatement extends CouplingControlStatement {
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
    	String ifGrammer = "\n";
    	String elseGrammer = "\n";
    	for (int i = 0; i<ifStatements.size();i++)
    	{
    		ifGrammer += ifStatements.get(i).getParsedGrammar();
    	}
    	
    	for (int i = 0; i<elseStatements.size();i++)
    	{
    		elseGrammer += elseStatements.get(i).getParsedGrammar();
    	}
    	
    	String grammer = "";
        if(!isLateStatement()) {
            grammer += "\n<block> -> <statement> <block> \n";
        } else {
            grammer += "\n<block> -> <statement> \n";
        }

        grammer += "<statement> -> <if_statement>\n"
        		+ "<if_statement> -> if <boolean_expression> <block> else <block> end\r\n"
                + booleanValueObject.getParsedGrammar()
                + ifGrammer 
                + elseGrammer
                ;
        return grammer;
    }

    public BooleanValueObject getBooleanValueObject() {
        return booleanValueObject;
    }

    public ArrayList<CouplingStatement> getIfStatements() {
        return ifStatements;
    }

    public ArrayList<CouplingStatement> getElseStatements() {
        return elseStatements;
    }

    @Override
    public void setLateStatement() {
        elseStatements.get(elseStatements.size()-1).setLateStatement();
    }
}
