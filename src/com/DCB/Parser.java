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
            for(Object object: analyzedScript) {
                System.out.println(object);
            }
        } else {
            System.out.println("Parsing Failed");
        }
    }





}
