package com.DCB.ParserObjects.Couplings.Operations.Boolean;

import com.DCB.LexicalObjects.KeyWord;
import com.DCB.ParserObjects.CoupledObject;
import com.DCB.ParserObjects.Value.BooleanValueObject;
import com.DCB.ParserObjects.Value.IntValueObject;
import com.DCB.ParserObjects.Value.StringValueObject;


/**
 * Coupling that supports the Boolean NotEquals operation
 */
public class CouplingBooleanNotEquals extends CoupledObject implements BooleanValueObject {
    private final boolean value;
    private final Object object1;
    private final Object object2;

    public CouplingBooleanNotEquals(Object object1, Object object2) {
        super(CoupleObjectType.BOOLEAN_NOT_EQUAl);
        if(object1 instanceof IntValueObject && object2 instanceof IntValueObject) {
            value = ((IntValueObject) object1).getValue() != ((IntValueObject) object2).getValue();
        } else {
            if (object1 instanceof BooleanValueObject && object2 instanceof BooleanValueObject) {
                value = ((BooleanValueObject) object1).getValue() != ((BooleanValueObject) object2).getValue();
            } else {
                if (object1 instanceof StringValueObject && object2 instanceof StringValueObject) {
                    value = !((StringValueObject) object1).getValue().equals(((StringValueObject) object2).getValue());
                } else {
                    value = false;
                }
            }
        }
        this.object1 = object1;
        this.object2 = object2;
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
        String identifier = "[" + coupleObjectType + " | ";
        if(object1 instanceof CoupledObject) {
            identifier += ((CoupledObject) object1).getStringIdentifier();
        } else {
            identifier += object1;
        }
        if(object2 instanceof CoupledObject) {
            identifier += ((CoupledObject) object2).getStringIdentifier();
        } else {
            identifier += object2;
        }
        identifier += " ]";
        return identifier;
    }

    @Override
    public String getParsedGrammar() {
        return "<boolean_expression> -> <relative_op> <arithmetic_expression> <arithmetic_expression>  \n"
        		+ "<relative_op> -> ne_operator\n"
        		//+ object1. .getParsedGrammar()
                //+ number2.getParsedGrammar()                    
                ;
    }


    @Override
    public boolean getValue() {
        return value;
    }


}