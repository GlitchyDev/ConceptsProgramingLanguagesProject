package com.DCB;

import com.DCB.ParserObjects.CouplingObjectFactory;

import java.util.ArrayList;

public class Parser {
    private final ArrayList<Object> analyzedScript;
    private final ArrayList<Integer> lineNumbers;
    private final CouplingObjectFactory couplingObjectFactory;
    private final int currentLineNumber;

    public Parser(ArrayList<Object> analyzedScript,ArrayList<Integer> lineNumbers, int linesRead) {
        this.analyzedScript = analyzedScript;
        this.lineNumbers = lineNumbers;
        this.currentLineNumber = linesRead;
        this.couplingObjectFactory = new CouplingObjectFactory(analyzedScript,lineNumbers,currentLineNumber);

        couplingObjectFactory.createAllCouplings();
    }

    public ArrayList<Object> getAnalyzedScript() {
        return analyzedScript;
    }

    public ArrayList<Integer> getLineNumbers() {
        return lineNumbers;
    }

    public int getLinesRead() {
        return currentLineNumber;
    }
}
