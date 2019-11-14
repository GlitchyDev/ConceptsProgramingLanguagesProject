package com.DCB.ParserObjects.Couplings.Statements;

import com.DCB.LexicalObjects.KeyWord;
import com.DCB.ParserObjects.CouplingStatement;
import com.DCB.ParserObjects.Couplings.Statements.Assignment.CouplingIntAssignment;
import com.DCB.ParserObjects.Value.Identifiers.IntIdentifierObject;
import com.DCB.ParserObjects.Value.IntValueObject;

/**
 * Used to represent the For loop Iter object
 */
public class CouplingIter extends CouplingStatement {
    private final CouplingIntAssignment couplingIntAssignment;
    private final IntValueObject intValueObject;

    public CouplingIter(CouplingIntAssignment couplingIntAssignment, IntValueObject intValueObject) {
        super(CoupleObjectType.ITER);
        this.couplingIntAssignment = couplingIntAssignment;
        this.intValueObject = intValueObject;
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
        return "[" + coupleObjectType + " | " + couplingIntAssignment.getStringIdentifier() +  " | " + intValueObject.getStringIdentifier() + " ]";
    }

    @Override
    public String getParsedGrammar() {
    	String grammer = "";
        

        grammer += "<iter> -> <arithmetic_expression> : <arithmetic_expression>\n"
                + couplingIntAssignment.getParsedGrammar()
                + intValueObject.getParsedGrammar()
                ;
                
        return grammer;
    }
}
