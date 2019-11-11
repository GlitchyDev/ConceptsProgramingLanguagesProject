package com.DCB.ParserObjects.Couplings.Operations.Boolean;

import com.DCB.LexicalObjects.KeyWord;
import com.DCB.ParserObjects.CoupledObject;
import com.DCB.ParserObjects.Value.BooleanValueObject;
import com.DCB.ParserObjects.Value.IntValueObject;

public class CouplingBooleanAnd extends CoupledObject implements BooleanValueObject {
    private final boolean value;
    private final BooleanValueObject number1;
    private final BooleanValueObject number2;

    public CouplingBooleanAnd(BooleanValueObject number1, BooleanValueObject number2) {
        super(CoupleObjectType.BOOLEAN_AND);
        value = number1.getValue() && number2.getValue();
        this.number1 = number1;
        this.number2 = number2;
    }

    @Override
    public boolean hasReturnType() {
        return true;
    }

    @Override
    public KeyWord.VariableType getReturnType() {
        return KeyWord.VariableType.BOOLEAN;
    }


    @Override
    public String getStringIdentifier() {
        return "[" + coupleObjectType + " | " + number1.getStringIdentifier() + " | " + number2.getStringIdentifier() + " ]";
    }

    @Override
    public String getParsedGrammar() {
        return "<boolean_expression> -> <relative_op> <arithmetic_expression> <arithmetic_expression>  \n"
        		+ "<relative_op> -> and_operator\n"
                + number1.getParsedGrammar()
                + number2.getParsedGrammar()                
                ;
    }


    @Override
    public boolean getValue() {
        return value;
    }


}
