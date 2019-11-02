package com.DCB.ParserObjects;

import com.DCB.LexicalObjects.Identifier;
import com.DCB.LexicalObjects.KeyWord;
import com.DCB.LexicalObjects.Value;
import com.DCB.ParserObjects.Value.*;

import java.util.ArrayList;

public class CouplingObjectFactory {
    private final ArrayList<Object> parsedScript;
    public CouplingObjectFactory(ArrayList<Object> parsedScript) {
        this.parsedScript = parsedScript;
    }


    public boolean createAllCouplings() {

        // Do until you can't create any more value or identifier couplings
                // Do all Value Couplings ( Prioritize () * / + -
                // Do all Identifier Assignment/Reassignment Couplings   <= The reason we wait is some might be dependant like
                         // <= The reason we wait is some might be dependant like     int a = 7 + 4     int b = 7 + a, if a isn't a value yet uh oh,
                         // <= We also do all avalible to prevent premature value encapsulation from assignment    int a = 7 + 4 + 7  ===>>> (int a = (7+4) + 7 ( Oh no! )
                // If any work was done, go up to Do all Value Couplings

        // Do all other Statements except for   IF THEN    WHILE    FOR   REPEAT Ect

        // Then do all the Statement containers



        return true;
    }



/*
switch(keyword) {
                case STRING_CONCAT:

                    break;
                case LESS_THAN:

                    break;
                case GREATER_THAN:

                    break;
                case EQUAL_LESS_THAN:

                    break;
                case EQUAL_GREATER_THAN:

                    break;
                case AND:

                    break;
                case EQUAL:

                    break;
                case NOT_EQUAL:

                    break;
                case NUMBER_ADD:

                    break;
                case NUMBER_SUBTRACT:

                    break;
                case MULTIPLY:

                    break;
                case DIVIDE:

                    break;
                case INVERT_DIVIDE:

                    break;
                case EXPONENTIAL:

                    break;
                case MOD:

                    break;
                case NUMBER_IDENTITY:

                    break;
                case NUMBER_INVERT:

                    break;
                case IF:

                    break;
                case ELSE:

                    break;
                case WHILE:

                    break;
                case DO:

                    break;
                case REPEAT:

                    break;
                case PRINT:

                    break;
                case ASSIGN:

                    break;
                case FOR:

                    break;
                case FOR_EACH:

                    break;
            }
 */

    private boolean isKeyword(Object object) {
        return (object instanceof KeyWord);
    }

    private boolean isIdentifier(Object object) {
        return (object instanceof Identifier);
    }

    private boolean isValue(Object object) {
        return (object instanceof Value);
    }

    private boolean isBooleanValueObject(Object object) {
        return (object instanceof BooleanValueObject);
    }

    private boolean isStringValueObject(Object object) {
        return (object instanceof StringValueObject);
    }

    private boolean isIntValueObject(Object object) {
        return (object instanceof IntValueObject);
    }

    private boolean isBooleanIdentifierObject(Object object) {
        return (object instanceof BooleanIdentifierObject);
    }

    private boolean isStringIdentifierObject(Object object) {
        return (object instanceof StringIdentifierObject);
    }

    private boolean isIntIdentifierObject(Object object) {
        return (object instanceof IntIdentifierObject);
    }

    private Object getObject(int i) {
        return parsedScript.get(i);
    }

    private Object getObjectOffset(int i, int offset) {
        return parsedScript.get(i+offset);
    }
}
