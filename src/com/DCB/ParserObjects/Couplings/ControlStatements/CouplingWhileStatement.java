package com.DCB.ParserObjects.Couplings.ControlStatements;

import com.DCB.LexicalObjects.KeyWord;
import com.DCB.ParserObjects.CouplingControlStatement;
import com.DCB.ParserObjects.CouplingStatement;
import com.DCB.ParserObjects.Value.BooleanValueObject;

import java.util.ArrayList;

/**
 * Control Statement encapsulation coupling for the While Control Statement
 */
public class CouplingWhileStatement extends CouplingControlStatement {
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

  //See CouplingForStatement.java for explanation.
    @Override
    public String getParsedGrammar() {
    	
    	//This will loop through the while block and pull the parsed grammar to be placed below.
    	String containGrammer = "\n";
    	for (int i = 0; i<containedStatements.size();i++)
    	{
    		containGrammer += containedStatements.get(i).getParsedGrammar();
    	}
    	
    	String grammer = "";
        if(!isLateStatement()) {
            grammer += "\n<block> -> <statement> <block>\n";
        } else {
            grammer += "\n<block> -> <statement>\n";
        }

        grammer += "<statement> -> <while_statement>\r\n" + 
        		"<while_statement> -> while <boolean_expression> <block> end\r\n"
                + booleanValueObject.getParsedGrammar()
                + containGrammer;
        return grammer;
    }

    public BooleanValueObject getBooleanValueObject() {
        return booleanValueObject;
    }

    public ArrayList<CouplingStatement> getContainedStatements() {
        return containedStatements;
    }

    @Override
    public void setLateStatement() {
        containedStatements.get(containedStatements.size()-1).setLateStatement();
    }
}


