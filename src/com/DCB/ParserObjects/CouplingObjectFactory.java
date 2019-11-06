package com.DCB.ParserObjects;

import com.DCB.LexicalObjects.Identifier;
import com.DCB.LexicalObjects.KeyWord;
import com.DCB.LexicalObjects.Value;
import com.DCB.ParserObjects.Couplings.*;
import com.DCB.ParserObjects.Value.*;
import com.DCB.ParserObjects.Value.Identifiers.BooleanIdentifierObject;
import com.DCB.ParserObjects.Value.Identifiers.IntIdentifierObject;
import com.DCB.ParserObjects.Value.Identifiers.StringIdentifierObject;
import com.DCB.ParserObjects.Value.Wrapper.BooleanValueWrapper;
import com.DCB.ParserObjects.Value.Wrapper.IntValueWrapper;
import com.DCB.ParserObjects.Value.Wrapper.StringValueWrapper;

import java.util.ArrayList;


/**
 * This is a Object that builds the Coupling required for the parser, it works by working with operations first, working its
 * way up into statements, then branching statements
 */
public class CouplingObjectFactory {
    private final KeyWord[] NUMBER_OPERATIONS = new KeyWord[]{KeyWord.LEFT_PARENTHESIS, KeyWord.EXPONENTIAL,KeyWord.MULTIPLY, KeyWord.DIVIDE,
            KeyWord.INVERT_DIVIDE, KeyWord.ADD, KeyWord.SUBTRACT, KeyWord.NUMBER_IDENTITY, KeyWord.NUMBER_INVERT, KeyWord.LESS_THAN, KeyWord.GREATER_THAN,KeyWord.EQUAL_LESS_THAN, KeyWord.EQUAL_GREATER_THAN,
            KeyWord.AND,KeyWord.EQUAL, KeyWord.NOT_EQUAL
    };

    private KeyWord[] FUNCTIONS = new KeyWord[]{KeyWord.ASSIGN,KeyWord.PRINT
    };

    private KeyWord[] CONTROL_STATEMENTS = new KeyWord[]{KeyWord.IF, KeyWord.ELSE, KeyWord.WHILE, KeyWord.DO, KeyWord.REPEAT,
            KeyWord.FOR, KeyWord.FOR_EACH
    };

    private final ArrayList<Object> parsedScript;
    private final ArrayList<Integer> lineNumbers;
    private final int currentLineNumber;


    public CouplingObjectFactory(ArrayList<Object> parsedScript, ArrayList<Integer> lineNumbers, int currentLineNumber) {
        this.parsedScript = parsedScript;
        this.lineNumbers = lineNumbers;
        this.currentLineNumber = currentLineNumber;
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
                        replace(i,new IntValueWrapper(value));
                        break;
                    case STRING:
                        replace(i,new StringValueWrapper(value));
                        break;
                    case BOOLEAN:
                        replace(i,new BooleanValueWrapper(value));
                        break;
                }
            }
            // Identifiers should actually be filled in once their assignment statement has initialized, but for now this will cheat.
            // The values of these identifiers will be what they where when initialized, mind you that will be incorrect
            // As the assignment a = 5*4 will initalize a = 5, but this is only to maintain typing
            if (object instanceof Identifier) {
                Identifier identifier = ((Identifier) object);
                // Ye
                switch (identifier.getVariableType()) {
                    case NUMBER:
                        replace(i,new IntIdentifierObject(identifier,new IntValueWrapper(identifier.getValue())));
                        break;
                    case STRING:
                        replace(i,new StringIdentifierObject(identifier,new StringValueWrapper(identifier.getValue())));
                        break;
                    case BOOLEAN:
                        replace(i,new BooleanIdentifierObject(identifier,new BooleanValueWrapper(identifier.getValue())));
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


    /**
     * Checks if a coupling can be created at the current location
     * @param keyword
     * @param couplingPosition
     * @return
     */
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
                    } else {
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
                    } else {
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
                if(getObject(couplingPosition-1) instanceof IntValueObject) {
                    if (getObject(couplingPosition + 1) instanceof IntValueObject) {
                        return true;
                    }
                }
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

    /**
     * Creates the coupling that has been verified to exist, just needs to replace the keywords 
     * @param keyword
     * @param couplingPosition
     */
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
                        replace(couplingPosition, couplingIntParentheses);
                        remove(couplingPosition+(2));
                        remove(couplingPosition+(1));
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
                        remove(couplingPosition+(1));
                        replace(couplingPosition,couplingIntAdd);
                        remove(couplingPosition+(-1));
                    } else {
                        // This is Number Identity
                    }
                } else {
                    if (getObject(couplingPosition + 1) instanceof StringValueObject) {
                        if (getObject(couplingPosition - 1) instanceof StringValueObject) {
                            CouplingStringConcat couplingStringConcat = new CouplingStringConcat(((StringValueObject) getObject(couplingPosition - 1)), ((StringValueObject) getObject(couplingPosition + 1)));
                            remove(couplingPosition + (1));
                            replace(couplingPosition, couplingStringConcat);
                            remove(couplingPosition + (-1));
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
                if(getObject(couplingPosition+1) instanceof IntValueObject) {
                    if(getObject(couplingPosition-1) instanceof IntValueObject) {
                        CouplingIntMultiply couplingIntMultiply = new CouplingIntMultiply(((IntValueObject)getObject(couplingPosition-1)),((IntValueObject)getObject(couplingPosition+1)));
                        remove(couplingPosition+(1));
                        replace(couplingPosition,couplingIntMultiply);
                        remove(couplingPosition+(-1));
                        // This is Number Identity
                    }
                }
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



    public void replace(int position, Object object) {
        parsedScript.set(position,object);
        lineNumbers.set(position,-1);
    }

    public void remove(int position) {
        parsedScript.remove(position);
        lineNumbers.remove(position);
    }

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

    private Object getObjectLineNumber(int i) {
        return lineNumbers.get(i);
    }

    private Object getObjectOffset(int i, int offset) {
        return parsedScript.get(i+offset);
    }
}
