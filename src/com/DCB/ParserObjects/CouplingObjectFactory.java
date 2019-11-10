package com.DCB.ParserObjects;

import com.DCB.LexicalObjects.Identifier;
import com.DCB.LexicalObjects.KeyWord;
import com.DCB.LexicalObjects.Value;
import com.DCB.ParserObjects.Couplings.Operations.*;
import com.DCB.ParserObjects.Couplings.Operations.Boolean.CouplingBooleanEqualGreaterThan;
import com.DCB.ParserObjects.Couplings.Operations.Boolean.CouplingBooleanEqualLessThan;
import com.DCB.ParserObjects.Couplings.Operations.Boolean.CouplingBooleanGreaterThan;
import com.DCB.ParserObjects.Couplings.Operations.Boolean.CouplingBooleanLessThan;
import com.DCB.ParserObjects.Couplings.Operations.Parentheses.CouplingBooleanParetheses;
import com.DCB.ParserObjects.Couplings.Operations.Parentheses.CouplingIntParentheses;
import com.DCB.ParserObjects.Couplings.Operations.Parentheses.CouplingStringParentheses;
import com.DCB.ParserObjects.Couplings.Statements.Assignment.CouplingBooleanAssignment;
import com.DCB.ParserObjects.Couplings.Statements.Assignment.CouplingIntAssignment;
import com.DCB.ParserObjects.Couplings.Statements.Assignment.CouplingStringAssignment;
import com.DCB.ParserObjects.Couplings.Statements.Print.CouplingBooleanPrint;
import com.DCB.ParserObjects.Couplings.Statements.Print.CouplingIntPrint;
import com.DCB.ParserObjects.Couplings.Statements.Print.CouplingStringPrint;
import com.DCB.ParserObjects.Value.*;
import com.DCB.ParserObjects.Value.Identifiers.BooleanIdentifierObject;
import com.DCB.ParserObjects.Value.Identifiers.IntIdentifierObject;
import com.DCB.ParserObjects.Value.Identifiers.StringIdentifierObject;
import com.DCB.ParserObjects.Value.Identifiers.UnidentifiedIdentifierObject;
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
            KeyWord.AND,KeyWord.EQUAL, KeyWord.NOT_EQUAL, KeyWord.ASSIGN
    };

    private KeyWord[] STATEMENTS = new KeyWord[]{KeyWord.PRINT
    };

    private KeyWord[] CONTROL_STATEMENTS = new KeyWord[]{KeyWord.IF, KeyWord.WHILE, //KeyWord.DO, KeyWord.REPEAT,
            KeyWord.FOR
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


    // So what if we looked ahead, and marked anything intertwine parenthesis as "Urgent Complete first",

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
                replace(i,new UnidentifiedIdentifierObject(identifier));
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
                            if(keyword == KeyWord.ADD) {
                                int xxx = 0;
                            }
                            if(canCreateCoupling(keyword,nextPotentialCouplingLocation)) {
                                createCoupling(keyword,nextPotentialCouplingLocation);
                                numberOfOperationsCompleted++;
                                completedKeywordCouplings = true;
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



        boolean completedAllStatementCouplings = false;
        while(!completedAllStatementCouplings) {
            int statementCompleteCount = 0;
            for(KeyWord currentKeyword: STATEMENTS) {
                int nextStatementCouplingLocation = -1;

                for(int i = 0; i < parsedScript.size(); i++) {
                    Object object = getObject(i);
                    if(object instanceof KeyWord && object == currentKeyword) {
                        nextStatementCouplingLocation = i;
                        break;
                    }
                }
                if(nextStatementCouplingLocation != -1) {
                    KeyWord keyword = (KeyWord) getObject(nextStatementCouplingLocation);
                    if(canCreateCoupling(keyword,nextStatementCouplingLocation)) {
                        createCoupling(keyword,nextStatementCouplingLocation);
                        statementCompleteCount++;
                    }
                }
            }
            if(statementCompleteCount == 0) {
                completedAllStatementCouplings = true;
            }
        }



        //
        boolean completedAllControlStatementCouplings = false;
        while(!completedAllControlStatementCouplings) {
            int controlStatementsComplete = 0;
            for(KeyWord currentKeyword: CONTROL_STATEMENTS) {
                int nextStatementCouplingLocation = -1;

                for(int i = 0; i < parsedScript.size(); i++) {
                    Object object = getObject(i);
                    if(object instanceof KeyWord && object == currentKeyword) {
                        nextStatementCouplingLocation = i;
                        break;
                    }
                }
                if(nextStatementCouplingLocation != -1) {
                    KeyWord keyword = (KeyWord) getObject(nextStatementCouplingLocation);
                    if(canCreateCoupling(keyword,nextStatementCouplingLocation)) {
                        createCoupling(keyword,nextStatementCouplingLocation);
                        controlStatementsComplete++;
                    }
                }
            }
            if(controlStatementsComplete == 0) {
                completedAllControlStatementCouplings= true;
            }
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
                if(getObject(couplingPosition+1) instanceof BooleanValueObject) {
                    int foundElseLine = -1;
                    int nonStatementFoundLine = -1;
                    for(int i = couplingPosition+4; i < parsedScript.size() - 1; i++) {
                        if(!(getObject(i) instanceof CouplingStatement)) {
                            if(getObject(i) instanceof KeyWord && getObject(i) == KeyWord.ELSE) {
                                foundElseLine = i;
                                break;
                            } else {
                                nonStatementFoundLine = getObjectLineNumber(i);
                                break;
                            }
                        }
                    }

                    if(nonStatementFoundLine != -1) {
                        System.out.println("Error found at Line " + nonStatementFoundLine + " non statement in if scope of Line " + couplingPosition);
                    }

                    if(foundElseLine != -1) {
                        int foundEndLine = -1;
                        nonStatementFoundLine = -1;
                        for(int i = foundElseLine+1; i < parsedScript.size() - 1; i++) {
                            if(!(getObject(i) instanceof CouplingStatement)) {
                                if(getObject(i) instanceof KeyWord && getObject(i) == KeyWord.END) {
                                    foundEndLine = i;
                                    break;
                                } else {
                                    nonStatementFoundLine = getObjectLineNumber(i);
                                    break;
                                }
                            }
                        }


                        if(nonStatementFoundLine != -1) {
                            System.out.println("Error found at Line " + nonStatementFoundLine + " non statement in if scope of Line " + couplingPosition);
                        }

                        if(foundEndLine != -1) {
                            return true;
                        }

                    } else {
                        System.out.println("Error found at Line " + couplingPosition + " no end to if statement ");

                    }
                } else {
                    System.out.println("Error found at Line " + couplingPosition + " no boolean found for if statement ");
                }
                break;
            case WHILE:
                if(getObject(couplingPosition+1) instanceof BooleanValueObject) {
                    int foundEndLine = -1;
                    int nonStatementFoundLine = -1;
                    for(int i = couplingPosition+4; i < parsedScript.size() - 1; i++) {
                        if(!(getObject(i) instanceof CouplingStatement)) {
                            if(getObject(i) instanceof KeyWord && getObject(i) == KeyWord.END) {
                                foundEndLine = i;
                                break;
                            } else {
                                nonStatementFoundLine = getObjectLineNumber(i);
                                break;
                            }
                        }
                    }

                    if(nonStatementFoundLine != -1) {
                        System.out.println("Error found at Line " + nonStatementFoundLine + " non statement in if scope of Line " + couplingPosition);
                    }

                    if(foundEndLine != -1) {
                        return true;
                    } else {
                        System.out.println("Error found at Line " + couplingPosition + " no end to if statement ");

                    }
                } else {
                    System.out.println("Error found at Line " + couplingPosition+1 + " no boolean value found for While Statement");
                }
                break;
            case FOR:
                if(getObject(couplingPosition+1) instanceof CouplingIntAssignment) {
                    if (getObject(couplingPosition + 2) instanceof KeyWord && getObject(couplingPosition + 2) == KeyWord.COLLEN) {
                        if (getObject(couplingPosition + 2) instanceof IntValueObject) {


                            int foundEndLine = -1;
                            int nonStatementFoundLine = -1;
                            for (int i = couplingPosition + 4; i < parsedScript.size() - 1; i++) {
                                if (!(getObject(i) instanceof CouplingStatement)) {
                                    if (getObject(i) instanceof KeyWord && getObject(i) == KeyWord.END) {
                                        foundEndLine = i;
                                        break;
                                    } else {
                                        nonStatementFoundLine = getObjectLineNumber(i);
                                        break;
                                    }
                                }
                            }

                            if (nonStatementFoundLine != -1) {
                                System.out.println("Error found at Line " + nonStatementFoundLine + " non statement in if scope of Line " + couplingPosition);
                            }

                            if (foundEndLine != -1) {
                                if (nonStatementFoundLine != -1) {
                                    System.out.println("Error found at Line " + nonStatementFoundLine + " non statement in if scope of Line " + couplingPosition);
                                }

                                if (foundEndLine != -1) {
                                    return true;
                                }

                            } else {
                                System.out.println("Error found at Line " + couplingPosition + " no end to For statement ");
                            }
                        } else {
                                System.out.println("Error found at Line " + couplingPosition + " No Iterator Cap found for Foor Loop");
                        }
                    } else {
                        System.out.println("Error found at Line " + couplingPosition + " No Colen found for the Foor Loop ");
                    }
                } else {
                    System.out.println("Error found at Line " + couplingPosition + " No Assingment statement for the For Loop ");
                }

                break;
                /*
            case DO:

                break;
            case REPEAT:

                break;
                */
            case PRINT:
                if(getObject(couplingPosition+1) instanceof IntValueObject) {
                    return true;
                }
                if(getObject(couplingPosition+1) instanceof BooleanValueObject) {
                    return true;
                }
                if(getObject(couplingPosition+1) instanceof StringValueObject) {
                    return true;
                }
                break;
            case ASSIGN:
                if(getObject(couplingPosition+1) instanceof IntValueObject) {
                    if(getObject(couplingPosition-1) instanceof IntIdentifierObject || getObject(couplingPosition-1) instanceof UnidentifiedIdentifierObject) {
                        return true;
                    }
                }
                if(getObject(couplingPosition+1) instanceof BooleanValueObject) {
                    if(getObject(couplingPosition-1) instanceof BooleanIdentifierObject || getObject(couplingPosition-1) instanceof UnidentifiedIdentifierObject) {
                        return true;
                    }
                }
                if(getObject(couplingPosition+1) instanceof StringValueObject) {
                    if(getObject(couplingPosition-1) instanceof StringIdentifierObject || getObject(couplingPosition-1) instanceof UnidentifiedIdentifierObject) {
                        return true;
                    }
                }

                break;
            case LEFT_PARENTHESIS:
                if(getObject(couplingPosition+1) instanceof IntValueObject) {
                    if(getObject(couplingPosition+2) instanceof KeyWord && getObject(couplingPosition+2)== KeyWord.RIGHT_PARENTHESIS) {
                        return true;
                    }
                }
                if(getObject(couplingPosition+1) instanceof IntValueObject) {
                    if(getObject(couplingPosition+2) instanceof KeyWord && getObject(couplingPosition+2)== KeyWord.RIGHT_PARENTHESIS) {
                        return true;
                    }
                }
                if(getObject(couplingPosition+1) instanceof BooleanValueObject) {
                    if(getObject(couplingPosition+2) instanceof KeyWord && getObject(couplingPosition+2)== KeyWord.RIGHT_PARENTHESIS) {
                        return true;
                    }
                }

                break;
            case LESS_THAN:
                if(getObject(couplingPosition+1) instanceof IntValueObject) {
                    if(getObject(couplingPosition+2) instanceof IntValueObject) {
                        return true;
                    }
                }
                break;
            case GREATER_THAN:
                if(getObject(couplingPosition+1) instanceof IntValueObject) {
                    if(getObject(couplingPosition+2) instanceof IntValueObject) {
                        return true;
                    }
                }
                break;
            case EQUAL_LESS_THAN:
                if(getObject(couplingPosition+1) instanceof IntValueObject) {
                    if(getObject(couplingPosition+2) instanceof IntValueObject) {
                        return true;
                    }
                }
                break;
            case EQUAL_GREATER_THAN:
                if(getObject(couplingPosition+1) instanceof IntValueObject) {
                    if(getObject(couplingPosition+2) instanceof IntValueObject) {
                        return true;
                    }
                }
                break;
            case AND:
                if(getObject(couplingPosition+1) instanceof BooleanValueObject) {
                    if(getObject(couplingPosition+2) instanceof BooleanValueObject) {
                        return true;
                    }
                }
                break;
            case EQUAL:
                if(getObject(couplingPosition+1) instanceof IntValueObject | getObject(couplingPosition+1) instanceof StringValueObject | getObject(couplingPosition+1) instanceof BooleanValueObject) {
                    if(getObject(couplingPosition+2) instanceof IntValueObject | getObject(couplingPosition+2) instanceof StringValueObject | getObject(couplingPosition+2) instanceof BooleanValueObject) {
                        return true;
                    }
                }
                break;
            case NOT_EQUAL:
                if(getObject(couplingPosition+1) instanceof IntValueObject | getObject(couplingPosition+1) instanceof StringValueObject | getObject(couplingPosition+1) instanceof BooleanValueObject) {
                    if(getObject(couplingPosition+2) instanceof IntValueObject | getObject(couplingPosition+2) instanceof StringValueObject | getObject(couplingPosition+2) instanceof BooleanValueObject) {
                        return true;
                    }
                }
                break;
            case ADD:
                if(getObject(couplingPosition+1) instanceof IntValueObject) {
                    if(getObject(couplingPosition+2) instanceof IntValueObject) {
                        return true;
                    } else {
                        // Have it check if there is a unresolved statement next to it, like a parenthis
                    }
                }
                if(getObject(couplingPosition+1) instanceof StringValueObject) {
                    if(getObject(couplingPosition+2) instanceof StringValueObject) {
                        return true;
                    }
                }
                break;
            case SUBTRACT:
                if(getObject(couplingPosition+1) instanceof IntValueObject) {
                    if(getObject(couplingPosition+2) instanceof IntValueObject) {
                        return true;
                    } else {
                        return true;
                    }
                }
                break;
            case MULTIPLY:
                if(getObject(couplingPosition+1) instanceof IntValueObject) {
                    if (getObject(couplingPosition + 2) instanceof IntValueObject) {
                        return true;
                    }
                }
                break;
            case DIVIDE:
                if(getObject(couplingPosition+1) instanceof IntValueObject) {
                    if (getObject(couplingPosition + 2) instanceof IntValueObject) {
                        return true;
                    }
                }
                break;
            case INVERT_DIVIDE:
                if(getObject(couplingPosition+1) instanceof IntValueObject) {
                    if (getObject(couplingPosition + 2) instanceof IntValueObject) {
                        return true;
                    }
                }
                break;
            case EXPONENTIAL:
                if(getObject(couplingPosition+1) instanceof IntValueObject) {
                    if (getObject(couplingPosition + 2) instanceof IntValueObject) {
                        return true;
                    }
                }
                break;
            case MOD:
                if(getObject(couplingPosition+1) instanceof IntValueObject) {
                    if (getObject(couplingPosition + 2) instanceof IntValueObject) {
                        return true;
                    }
                }
                break;
            case NUMBER_IDENTITY:
                if(getObject(couplingPosition+1) instanceof IntValueObject) {
                    return true;
                }
                break;
            case NUMBER_INVERT:
                if(getObject(couplingPosition+1) instanceof IntValueObject) {
                    return true;
                }
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
                System.out.println("We got the if working!");
                break;
            case WHILE:
                System.out.println("We got the While working!");
                break;
                /*
            case DO:

                break;
            case REPEAT:

                break;
                */
            case PRINT:
                if(getObject(couplingPosition+1) instanceof StringValueObject) {
                    CouplingStringPrint couplingStringPrint = new CouplingStringPrint((StringValueObject) getObject(couplingPosition+1));
                    replace(couplingPosition,couplingStringPrint);
                    remove(couplingPosition+1);
                } else {
                    if (getObject(couplingPosition + 1) instanceof IntValueObject) {
                        CouplingIntPrint couplingIntPrint = new CouplingIntPrint((IntValueObject) getObject(couplingPosition + 1));
                        replace(couplingPosition, couplingIntPrint);
                        remove(couplingPosition + 1);
                    } else {
                        if (getObject(couplingPosition + 1) instanceof BooleanValueObject) {
                            CouplingBooleanPrint couplingBooleanPrint = new CouplingBooleanPrint((BooleanValueObject) getObject(couplingPosition + 1));
                            replace(couplingPosition, couplingBooleanPrint);
                            remove(couplingPosition + 1);
                        }
                    }
                }
                break;
            case ASSIGN:
                if(getObject(couplingPosition+1) instanceof StringValueObject) {
                    StringIdentifierObject stringIdentifierObject = null;
                    if(getObject(couplingPosition-1) instanceof UnidentifiedIdentifierObject) {
                        stringIdentifierObject = wrapStringIdentifier(((UnidentifiedIdentifierObject)getObject(couplingPosition-1)),((StringValueObject)getObject(couplingPosition+1)));
                    } else {
                        if(getObject(couplingPosition-1) instanceof StringIdentifierObject) {
                            stringIdentifierObject = (StringIdentifierObject) getObject(couplingPosition-1);
                            stringIdentifierObject.setStringValueObject((StringValueObject) getObject(couplingPosition+1));
                        }
                    }

                    Identifier identifier = stringIdentifierObject.getIdentifier();
                    for(int i = currentLineNumber; i < parsedScript.size(); i++) {
                        if(getObject(i) instanceof UnidentifiedIdentifierObject) {
                            if(((UnidentifiedIdentifierObject) getObject(i)).getIdentifier().equals(identifier)) {
                                replace(i,stringIdentifierObject);
                            }
                        }
                        if(getObject(i) instanceof StringIdentifierObject) {
                            if(((StringIdentifierObject) getObject(i)).getIdentifier().equals(identifier)) {
                                replace(i,stringIdentifierObject);
                            }
                        }
                    }


                    CouplingStringAssignment couplingStringAssignment = new CouplingStringAssignment(stringIdentifierObject,((StringValueObject)getObject(couplingPosition+1)));
                    replace(couplingPosition, couplingStringAssignment);
                    remove(couplingPosition+1);
                    remove(couplingPosition-1);
                } else {

                    if (getObject(couplingPosition + 1) instanceof IntValueObject) {
                        IntIdentifierObject intIdentifierObject = null;
                        if (getObject(couplingPosition - 1) instanceof UnidentifiedIdentifierObject) {
                            intIdentifierObject = wrapIntegerIdentifier(((UnidentifiedIdentifierObject) getObject(couplingPosition - 1)), ((IntValueObject) getObject(couplingPosition + 1)));
                        } else {
                            if (getObject(couplingPosition - 1) instanceof StringIdentifierObject) {
                                intIdentifierObject = (IntIdentifierObject) getObject(couplingPosition - 1);
                                intIdentifierObject.setIntValueObject((IntValueObject) getObject(couplingPosition + 1));
                            }
                        }

                        Identifier identifier = intIdentifierObject.getIdentifier();
                        for (int i = couplingPosition - 1; i < parsedScript.size(); i++) {
                            if (getObject(i) instanceof UnidentifiedIdentifierObject) {
                                if (((UnidentifiedIdentifierObject) getObject(i)).getIdentifier().getIdentifier().equals(identifier.getIdentifier())) {
                                    replace(i, intIdentifierObject);
                                }
                            }
                            if (getObject(i) instanceof IntIdentifierObject) {
                                if (((IntIdentifierObject) getObject(i)).getIdentifier().equals(identifier)) {
                                    replace(i, intIdentifierObject);
                                }
                            }
                        }


                        CouplingIntAssignment couplingIntAssignment = new CouplingIntAssignment(intIdentifierObject, ((IntValueObject) getObject(couplingPosition + 1)));
                        replace(couplingPosition, couplingIntAssignment);
                        remove(couplingPosition + 1);
                        remove(couplingPosition - 1);
                    } else {
                        if (getObject(couplingPosition + 1) instanceof BooleanValueObject) {
                            BooleanIdentifierObject booleanIdentifierObject = null;
                            if (getObject(couplingPosition - 1) instanceof UnidentifiedIdentifierObject) {
                                booleanIdentifierObject = wrapBooleanIdentifier(((UnidentifiedIdentifierObject) getObject(couplingPosition - 1)), ((BooleanValueObject) getObject(couplingPosition + 1)));
                            } else {
                                if (getObject(couplingPosition - 1) instanceof BooleanIdentifierObject) {
                                    booleanIdentifierObject = (BooleanIdentifierObject) getObject(couplingPosition - 1);
                                    booleanIdentifierObject.setBooleanValueObject((BooleanValueObject) getObject(couplingPosition + 1));
                                }
                            }

                            Identifier identifier = booleanIdentifierObject.getIdentifier();
                            for (int i = currentLineNumber; i < parsedScript.size(); i++) {
                                if (getObject(i) instanceof UnidentifiedIdentifierObject) {
                                    if (((UnidentifiedIdentifierObject) getObject(i)).getIdentifier().equals(identifier)) {
                                        replace(i, booleanIdentifierObject);
                                    }
                                }
                                if (getObject(i) instanceof StringIdentifierObject) {
                                    if (((StringIdentifierObject) getObject(i)).getIdentifier().equals(identifier)) {
                                        replace(i, booleanIdentifierObject);
                                    }
                                }
                            }


                            CouplingBooleanAssignment couplingBooleanAssignment = new CouplingBooleanAssignment(booleanIdentifierObject, ((BooleanValueObject) getObject(couplingPosition + 1)));
                            replace(couplingPosition, couplingBooleanAssignment);
                            remove(couplingPosition + 1);
                            remove(couplingPosition - 1);
                        }
                    }
                }
                break;
            case FOR:

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
                    } else {
                        if (getObject(couplingPosition + 1) instanceof BooleanValueObject) {
                            if(getObject(couplingPosition+2) instanceof KeyWord && getObject(couplingPosition+2) == KeyWord.RIGHT_PARENTHESIS) {
                                CouplingBooleanParetheses couplingBooleanParetheses = new CouplingBooleanParetheses(((BooleanValueObject) getObject(couplingPosition + 1)));
                                parsedScript.set(couplingPosition, couplingBooleanParetheses);
                                parsedScript.remove(couplingPosition + (2));
                                parsedScript.remove(couplingPosition + (1));
                            }
                        }
                    }
                }


                break;
            case LESS_THAN:
                CouplingBooleanLessThan couplingBooleanLessThan = new CouplingBooleanLessThan(((IntValueObject)getObject(couplingPosition+1)),((IntValueObject)getObject(couplingPosition+2)));
                remove(couplingPosition+(2));
                replace(couplingPosition,couplingBooleanLessThan);
                remove(couplingPosition+(1));
                break;
            case GREATER_THAN:
                CouplingBooleanGreaterThan couplingBooleanGreaterThan = new CouplingBooleanGreaterThan(((IntValueObject)getObject(couplingPosition+1)),((IntValueObject)getObject(couplingPosition+2)));
                remove(couplingPosition+(2));
                replace(couplingPosition,couplingBooleanGreaterThan);
                remove(couplingPosition+(1));
                break;
            case EQUAL_LESS_THAN:
                CouplingBooleanEqualLessThan couplingBooleanEqualLessThan = new CouplingBooleanEqualLessThan(((IntValueObject)getObject(couplingPosition+1)),((IntValueObject)getObject(couplingPosition+2)));
                remove(couplingPosition+(2));
                replace(couplingPosition,couplingBooleanEqualLessThan);
                remove(couplingPosition+(1));
                break;
            case EQUAL_GREATER_THAN:
                CouplingBooleanEqualGreaterThan couplingBooleanEqualGreaterThan = new CouplingBooleanEqualGreaterThan(((IntValueObject)getObject(couplingPosition+1)),((IntValueObject)getObject(couplingPosition+2)));
                remove(couplingPosition+(2));
                replace(couplingPosition,couplingBooleanEqualGreaterThan);
                remove(couplingPosition+(1));
                break;
            case AND:

                break;
            case EQUAL:

                break;
            case NOT_EQUAL:

                break;
            case ADD:
                if(getObject(couplingPosition+1) instanceof IntValueObject) {
                    if(getObject(couplingPosition+2) instanceof IntValueObject) {
                        CouplingIntAdd couplingIntAdd = new CouplingIntAdd(((IntValueObject)getObject(couplingPosition+1)),((IntValueObject)getObject(couplingPosition+2)));
                        remove(couplingPosition+(2));
                        replace(couplingPosition,couplingIntAdd);
                        remove(couplingPosition+(1));
                    } else {
                        // This is Number Identity
                    }
                } else {
                    if (getObject(couplingPosition + 1) instanceof StringValueObject) {
                        if (getObject(couplingPosition + 2) instanceof StringValueObject) {
                            CouplingStringConcat couplingStringConcat = new CouplingStringConcat(((StringValueObject) getObject(couplingPosition + 1)), ((StringValueObject) getObject(couplingPosition + 2)));
                            remove(couplingPosition + (2));
                            replace(couplingPosition, couplingStringConcat);
                            remove(couplingPosition + (1));
                        }
                    }
                }
                break;
            case SUBTRACT:
                if(getObject(couplingPosition+1) instanceof IntValueObject) {
                    if(getObject(couplingPosition+2) instanceof IntValueObject) {

                    } else {
                        // This is Number Invert
                    }
                }
                break;
            case MULTIPLY:
                if(getObject(couplingPosition+1) instanceof IntValueObject) {
                    if(getObject(couplingPosition+2) instanceof IntValueObject) {
                        CouplingIntMultiply couplingIntMultiply = new CouplingIntMultiply(((IntValueObject)getObject(couplingPosition+1)),((IntValueObject)getObject(couplingPosition+2)));
                        remove(couplingPosition+(2));
                        replace(couplingPosition,couplingIntMultiply);
                        remove(couplingPosition+(1));
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


    public StringIdentifierObject wrapStringIdentifier(UnidentifiedIdentifierObject unidentifiedIdentifierObject, StringValueObject stringValueObject) {
        Identifier identifier = unidentifiedIdentifierObject.getIdentifier();
        StringIdentifierObject stringIdentifierObject = new StringIdentifierObject(identifier, stringValueObject);
        return stringIdentifierObject;
    }

    public IntIdentifierObject wrapIntegerIdentifier(UnidentifiedIdentifierObject unidentifiedIdentifierObject, IntValueObject intValueObject) {
        Identifier identifier = unidentifiedIdentifierObject.getIdentifier();
        IntIdentifierObject intIdentifierObject = new IntIdentifierObject(identifier, intValueObject);
        return intIdentifierObject;
    }

    public BooleanIdentifierObject wrapBooleanIdentifier(UnidentifiedIdentifierObject unidentifiedIdentifierObject, BooleanValueObject booleanValueObject) {
        Identifier identifier = unidentifiedIdentifierObject.getIdentifier();
        BooleanIdentifierObject booleanIdentifierObject = new BooleanIdentifierObject(identifier, booleanValueObject);
        return booleanIdentifierObject;
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

    private int getObjectLineNumber(int i) {
        return lineNumbers.get(i);
    }

    private Object getObjectOffset(int i, int offset) {
        return parsedScript.get(i+offset);
    }
}
