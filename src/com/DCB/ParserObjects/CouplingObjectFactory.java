package com.DCB.ParserObjects;

import com.DCB.LexicalObjects.Identifier;
import com.DCB.LexicalObjects.KeyWord;
import com.DCB.LexicalObjects.Value;
import com.DCB.ParserObjects.Couplings.CouplingIntParentheses;
import com.DCB.ParserObjects.Couplings.CouplingIntAdd;
import com.DCB.ParserObjects.Couplings.CouplingStringConcat;
import com.DCB.ParserObjects.Couplings.CouplingStringParentheses;
import com.DCB.ParserObjects.Value.*;
import com.DCB.ParserObjects.Value.Identifiers.BooleanIdentifierObject;
import com.DCB.ParserObjects.Value.Identifiers.IntIdentifierObject;
import com.DCB.ParserObjects.Value.Identifiers.StringIdentifierObject;
import com.DCB.ParserObjects.Value.Wrapper.BooleanValueWrapper;
import com.DCB.ParserObjects.Value.Wrapper.IntValueWrapper;
import com.DCB.ParserObjects.Value.Wrapper.StringValueWrapper;

import java.util.ArrayList;

public class CouplingObjectFactory {
    private final KeyWord[] NUMBER_OPERATIONS = new KeyWord[]{KeyWord.EXPONENTIAL,KeyWord.MULTIPLY, KeyWord.DIVIDE,
            KeyWord.INVERT_DIVIDE, KeyWord.ADD, KeyWord.SUBTRACT, KeyWord.NUMBER_IDENTITY, KeyWord.NUMBER_INVERT, KeyWord.LESS_THAN, KeyWord.GREATER_THAN,KeyWord.EQUAL_LESS_THAN, KeyWord.EQUAL_GREATER_THAN,
            KeyWord.AND,KeyWord.EQUAL, KeyWord.NOT_EQUAL,KeyWord.LEFT_PARENTHESIS
    };

    private KeyWord[] FUNCTIONS = new KeyWord[]{KeyWord.IF, KeyWord.ELSE, KeyWord.WHILE, KeyWord.DO, KeyWord.REPEAT,
            KeyWord.FOR, KeyWord.FOR_EACH,KeyWord.PRINT,KeyWord.PRINT,KeyWord.ASSIGN
    };
    private final ArrayList<Object> parsedScript;

    public CouplingObjectFactory(ArrayList<Object> parsedScript) {
        this.parsedScript = parsedScript;
    }



    // Do until you can't create any more value or identifier couplings
    // Do all Value Couplings ( Prioritize () * / + -
    // Do all Identifier Assignment/Reassignment Couplings   <= The reason we wait is some might be dependant like
    // <= The reason we wait is some might be dependant like     int a = 7 + 4     int b = 7 + a, if a isn't a value yet uh oh,
    // <= We also do all avalible to prevent premature value encapsulation from assignment    int a = 7 + 4 + 7  ===>>> (int a = (7+4) + 7 ( Oh no! )
    // If any work was done, go up to Do all Value Couplings

    // Do all other Statements except for   IF THEN    WHILE    FOR   REPEAT Ect

    // Then do all the Statement containers
    public boolean createAllCouplings() {
        // Check and removal all raw values and replace with other couplings
        for(int i = 0; i < parsedScript.size(); i++) {
            Object object = getObject(i);
            if (object instanceof Value) {
                Value value = (Value) object;
                switch (value.getVariableType()) {
                    case NUMBER:
                        parsedScript.set(i, new IntValueWrapper(value));
                        break;
                    case STRING:
                        parsedScript.set(i, new StringValueWrapper(value));
                        break;
                    case BOOLEAN:
                        parsedScript.set(i, new BooleanValueWrapper(value));
                        break;
                }
            }
        }

        boolean completedAllValueIdentifierCouplings = false;
        while(!completedAllValueIdentifierCouplings) {

            boolean completedAllValueCouplings = false;
            while(!completedAllValueCouplings) {
                int numberOfOperationsCompleted = 0;

                for (KeyWord currentKeyword : NUMBER_OPERATIONS) {
                    boolean completedKeywordCouplings = false;
                    ArrayList<Integer> failedCouplings = new ArrayList<>();
                    while(!completedKeywordCouplings) {
                        int nextPotentialCouplingLocation = -1;

                        for(int i = 0; i < parsedScript.size(); i++) {
                            Object object = getObject(i);
                            if(object instanceof KeyWord && object == currentKeyword && !failedCouplings.contains(i)) {
                                nextPotentialCouplingLocation = i;
                                break;
                            }
                        }
                        if(nextPotentialCouplingLocation != -1) {
                            KeyWord keyword = (KeyWord) getObject(nextPotentialCouplingLocation);
                            if(canCreateCoupling(keyword,nextPotentialCouplingLocation)) {
                                createCoupling(keyword,nextPotentialCouplingLocation);
                                numberOfOperationsCompleted++;
                            } else {
                                failedCouplings.add(nextPotentialCouplingLocation);
                            }
                        } else {
                            completedKeywordCouplings = true;
                        }
                    }
                }
                if(numberOfOperationsCompleted == 0) {
                    completedAllValueCouplings = true;
                }

            }
            /*
            boolean completedAllIdentifierCouplings = false;
            while(!completedAllIdentifierCouplings) {
                for(int i = 0; i < parsedScript.size(); i++) {

                }
            }
            */
            completedAllValueIdentifierCouplings = true;
        }
        return true;
    }



    public boolean canCreateCoupling(KeyWord keyword,int couplingPosition) {
        switch(keyword) {
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
            case LEFT_PARENTHESIS:
                if(getObject(couplingPosition+1) instanceof StringValueObject) {
                    if(getObject(couplingPosition+2) instanceof KeyWord && getObject(couplingPosition+2)== KeyWord.RIGHT_PARENTHESIS) {
                        return true;
                    }
                }
                if(getObject(couplingPosition+1) instanceof IntValueObject) {
                    if(getObject(couplingPosition+2) instanceof KeyWord && getObject(couplingPosition+2)== KeyWord.RIGHT_PARENTHESIS) {
                        return true;
                    }
                }
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
            case ADD:
                if(getObject(couplingPosition-1) instanceof IntValueObject) {
                    if(getObject(couplingPosition+1) instanceof IntValueObject) {
                        return true;
                    }
                }
                if(getObject(couplingPosition-1) instanceof StringValueObject) {
                    if(getObject(couplingPosition+1) instanceof StringValueObject) {
                        return true;
                    }
                }
                break;
            case SUBTRACT:
                if(getObject(couplingPosition-1) instanceof IntValueObject) {
                    if(getObject(couplingPosition+1) instanceof IntValueObject) {
                        return true;
                    }
                }
                if(getObject(couplingPosition-1) instanceof StringValueObject) {
                    if(getObject(couplingPosition+1) instanceof StringValueObject) {
                        return true;
                    }
                }
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
        }
        return false;
    }

    public void createCoupling(KeyWord keyword,int couplingPosition) {
        switch(keyword) {
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
            case LEFT_PARENTHESIS:
                if(getObject(couplingPosition+1) instanceof IntValueObject) {
                    if(getObject(couplingPosition+2) instanceof KeyWord && getObject(couplingPosition+2) == KeyWord.RIGHT_PARENTHESIS) {
                        CouplingIntParentheses couplingIntParentheses = new CouplingIntParentheses(((IntValueObject)getObject(couplingPosition+1)));
                        parsedScript.set(couplingPosition, couplingIntParentheses);
                        parsedScript.remove(couplingPosition+(2));
                        parsedScript.remove(couplingPosition+(1));
                    }
                } else {
                    if (getObject(couplingPosition + 1) instanceof StringValueObject) {
                        if(getObject(couplingPosition+2) instanceof KeyWord && getObject(couplingPosition+2) == KeyWord.RIGHT_PARENTHESIS) {
                            CouplingStringParentheses couplingStringParentheses = new CouplingStringParentheses(((StringValueObject) getObject(couplingPosition + 1)));
                            parsedScript.set(couplingPosition, couplingStringParentheses);
                            parsedScript.remove(couplingPosition + (2));
                            parsedScript.remove(couplingPosition + (1));
                        }
                    }
                }


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
            case ADD:
                if(getObject(couplingPosition+1) instanceof IntValueObject) {
                    if(getObject(couplingPosition-1) instanceof IntValueObject) {
                        CouplingIntAdd couplingIntAdd = new CouplingIntAdd(((IntValueObject)getObject(couplingPosition-1)),((IntValueObject)getObject(couplingPosition+1)));
                        parsedScript.remove(couplingPosition+(1));
                        parsedScript.set(couplingPosition,couplingIntAdd);
                        parsedScript.remove(couplingPosition+(-1));                    } else {
                        // This is Number Identity
                    }
                } else {
                    if (getObject(couplingPosition + 1) instanceof StringValueObject) {
                        if (getObject(couplingPosition - 1) instanceof StringValueObject) {
                            CouplingStringConcat couplingStringConcat = new CouplingStringConcat(((StringValueObject) getObject(couplingPosition - 1)), ((StringValueObject) getObject(couplingPosition + 1)));
                            parsedScript.remove(couplingPosition + (1));
                            parsedScript.set(couplingPosition, couplingStringConcat);
                            parsedScript.remove(couplingPosition + (-1));
                        }
                    }
                }
                break;
            case SUBTRACT:
                if(getObject(couplingPosition-1) instanceof IntValueObject) {
                    if(getObject(couplingPosition+1) instanceof IntValueObject) {

                    } else {
                        // This is Number Invert
                    }
                }
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
        }
    }

    /*
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
     */


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
