package com.DCB;

import com.DCB.ParserObjects.CouplingObjectFactory;

import java.util.ArrayList;

public class Parser {
    private final ArrayList<Object> analyzedScript;
    private final CouplingObjectFactory couplingObjectFactory;

    public Parser(ArrayList<Object> analyzedScript) {
        this.analyzedScript = analyzedScript;
        this.couplingObjectFactory = new CouplingObjectFactory(analyzedScript);

        if(couplingObjectFactory.createAllCouplings()) {
            System.out.println("Parsing Completed!");
        } else {
            System.out.println("Parsing Failed");
        }
    }

    public ArrayList<Object> getAnalyzedScript() {
        return analyzedScript;
    }
}
