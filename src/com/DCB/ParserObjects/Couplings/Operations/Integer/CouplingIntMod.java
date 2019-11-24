package com.DCB.ParserObjects.Couplings.Operations.Integer;

import com.DCB.LexicalObjects.KeyWord;
import com.DCB.ParserObjects.CouplingObject;
import com.DCB.ParserObjects.Value.IntValueObject;

/**
 * Coupling that supports the Integer Mod operation
 */
public class CouplingIntMod extends CouplingObject implements IntValueObject {
    private final IntValueObject number1;
    private final IntValueObject number2;

    public CouplingIntMod(IntValueObject number1, IntValueObject number2) {
        super(CoupleObjectType.NUMBER_MOD);
        this.number1 = number1;
        this.number2 = number2;
    }

    @Override
    public boolean hasReturnType() {
        return true;
    }

    @Override
    public KeyWord.VariableType getReturnType() {
        return KeyWord.VariableType.NUMBER;
    }


    @Override
    public String getStringIdentifier() {
        return "[" + coupleObjectType + " | " + number1.getStringIdentifier() + " | " + number2.getStringIdentifier() + " ]";
    }

  //See CouplingForStatement.java for explanation.
    @Override
    public String getParsedGrammar() {
        return "<arithmetic_expression> -> <binary_expression> \n"
                + "<binary_expression> -> <arithmetic_op> <arithmetic_expression> <arithmetic_expression> \n"
                + "<arithmetic_op> -> mod_operator\n"
                + number1.getParsedGrammar() 
                + number2.getParsedGrammar();
    }


    @Override
    public int getValue() {
        return number1.getValue() % number2.getValue();
    }


}
